package api.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.jetty.server.handler.ContextHandler.Availability;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.BehavioralFeature;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.StructuralFeature;
import org.eclipse.uml2.uml.UMLFactory;

import com.google.gson.internal.LinkedTreeMap;

import api.classes.DomainAttribute;
import api.classes.DomainClass;
import api.classes.DomainOperation;
import api.classes.DomainRelationship;
import api.classes.DomainRelationshipEnd;
import api.classes.MockupGeneralElement;
import api.classes.MockupMultipleColumnElement;
import api.classes.MockupSingleColumnElement;
import api.classes.NavigationEvent;
import api.classes.Parameter;
import ifml.core.Action;
import ifml.core.ActionEvent;
import ifml.core.BehavioralFeatureConcept;
import ifml.core.ConditionalExpression;
import ifml.core.CoreFactory;
import ifml.core.DataBinding;
import ifml.core.DomainConcept;
import ifml.core.DomainModel;
import ifml.core.DynamicBehavior;
import ifml.core.FeatureConcept;
import ifml.core.NavigationFlow;
import ifml.core.ParameterBinding;
import ifml.core.ParameterBindingGroup;
import ifml.core.ParameterKind;
import ifml.core.UMLBehavioralFeature;
import ifml.core.UMLDomainConcept;
import ifml.core.UMLStructuralFeature;
import ifml.core.ViewContainer;
import ifml.core.ViewElement;
import ifml.core.ViewElementEvent;
import ifml.core.VisualizationAttribute;
import ifml.extensions.Button;
import ifml.extensions.Details;
import ifml.extensions.ExtensionsFactory;
import ifml.extensions.Form;
import ifml.extensions.Image;
import ifml.extensions.List;
import ifml.extensions.SelectEvent;
import ifml.extensions.SelectionField;
import ifml.extensions.SimpleField;
import ifml.extensions.SubmitEvent;
import ifml.extensions.TextField;
import ifml.extensions.Video;
import ifml.extensions.Window;

public class ElementBuilder {
	
    public static CoreFactory f;
    public static ExtensionsFactory ef;
    public static UMLFactory umlf;
	public static TypeFactory tf;
	public static EcoreFactory ecf;
	
	public Map<String, FeatureConcept> mapAttributes;
	public Map<String, BehavioralFeatureConcept> mapOperations;
	public Map<String, DomainConcept> mapClass;
	public ArrayList<DataBinding> listDataBinding;
	public ArrayList<SubmitEvent> listSubmitEvent; 

	public ElementBuilder(CoreFactory pf, ExtensionsFactory pef, UMLFactory uf) {
		f = pf;
		ef = pef;
		umlf = uf;
		tf = new TypeFactory(umlf);
		listDataBinding = new ArrayList<DataBinding>();
		listSubmitEvent = new ArrayList<SubmitEvent>();
		mapClass = new HashMap<String, DomainConcept>();
		mapOperations = new HashMap<String, BehavioralFeatureConcept>();
		mapAttributes = new HashMap<String, FeatureConcept>();
	}
	
	public ArrayList<PrimitiveType> getTypes(){
		return tf.getTypes();
	}

	/**
	 * Creates a Window based on a MockupElement.
	 * 
	 * @param elem - MockupElement with all the relevant info.
	 * @return     - Window object.
	 */
	public Window createWindow(MockupSingleColumnElement elem) {
		
		Window window = ef.createWindow();
		window.setId(elem.getId());
		window.setName(elem.getName());
		window.setIsModal(true);
		
		return window;
		
	}

	/**
	 * Creates a List based on a MockupElement.
	 * 
	 * @param elem - MockupElement with all the relevant info.
	 * @return     - List object.
	 */
	public List createList(MockupMultipleColumnElement elem, ArrayList<LinkElem> links) {
		
		List list = ef.createList();
		list.setId(elem.getId());
		list.setName(elem.getName());
		
		if(elem.getEvents() != null && !elem.getEvents().get(0).getLink().equals("")) {
			SelectEvent se = createSelectEvent(elem);
			NavigationEvent ne = elem.getEvents().get(0);
			NavigationFlow nf = f.createNavigationFlow();

			nf.setSrcInteractionFlowElement(se);
			se.getNavigationFlows().add(nf);	
			list.getViewElementEvents().add(se);
			LinkElem le = new LinkElem();
			le.setId(ne.getLink());
			le.setNavigationFlow(nf);
			
			links.add(le);
		}
		
		HashMap<String, Object> properties = elem.getProperties();
		
		if (properties.containsKey("entity")) {
				String entity = (String)properties.get("entity");
				DataBinding db = f.createDataBinding();				
				db.setName("dataBinding_"+ entity);
				DomainConcept dc = mapClass.get(entity);				
				db.setDomainConcept(dc);
				if (properties.containsKey("attributes")) {
					for (LinkedTreeMap<String, Object> a: (ArrayList<LinkedTreeMap<String, Object>>)properties.get("attributes")) {						
						if(a.get("type").equals("association")){														
							LinkedTreeMap<String, Object> props = (LinkedTreeMap<String, Object>)a.get("properties");
							String clase = (String)(props.get("clase"));
							DataBinding dba = f.createDataBinding();
							dba.setName("dataBinding_"+ clase);
							UMLDomainConcept dca = (UMLDomainConcept)mapClass.get(clase);
							dba.setDomainConcept(dca);
							for (Property p: dca.getClassifier().getAttributes()) {
								VisualizationAttribute va = f.createVisualizationAttribute();						
								va.setFeatureConcept(mapAttributes.get(dca.getName() + "_" + p.getName()));
								va.setId(va.getFeatureConcept().getId());
								va.setName(va.getFeatureConcept().getName());
								dba.getVisualizationAttributes().add(va);								
							}	
							list.getViewComponentParts().add(dba);
							listDataBinding.add(dba);
						}
						else{
							VisualizationAttribute va = f.createVisualizationAttribute();						
							va.setFeatureConcept(mapAttributes.get(dc.getName() + "_" + (String)(((LinkedTreeMap<String, Object>)a.get("properties")).get("nombre"))));
							va.setId(va.getFeatureConcept().getId());
							va.setName(va.getFeatureConcept().getName());
							db.getVisualizationAttributes().add(va);	
							listDataBinding.add(db);
						}																								
					}
					
				}
				list.getViewComponentParts().add(db);										
		}
		
		return list;
		
	}

	/**
	 * Creates a Button based on a MockupElement.
	 * 
	 * @param elem - MockupElement with all the relevant info.
	 * @return     - Button object.
	 */
	public Button createButton(MockupMultipleColumnElement elem, ArrayList<LinkElem> links) {		
		Button button = ef.createButton();
		button.setId(elem.getId());
		button.setName(elem.getName());
		HashMap<String, Object> properties = elem.getProperties();
		
		if (properties.containsKey("align")) {
			button.setAlign((String) properties.get("align")); 
		}
		
		if (properties.containsKey("value")) {
			button.setButtonText((String)elem.getProperties().get("value"));
		}

		if(elem.getEvents() != null){
			for(NavigationEvent navEvent: elem.getEvents()){
				
				ViewElementEvent event = f.createViewElementEvent();
				NavigationFlow nf = f.createNavigationFlow();
				
				nf.setSrcInteractionFlowElement(event);
				event.getNavigationFlows().add(nf);
				button.getViewElementEvents().add(event);
				LinkElem le = new LinkElem();
				le.setId(navEvent.getLink());
				le.setNavigationFlow(nf);
				
				links.add(le);
				
			}
		}
	    
		return button;
		
	}

	/**
	 * Creates a TextField based on a MockupElement.
	 * 
	 * @param elem - MockupElement with all the relevant info.
	 * @return     - TextField object.
	 */
	public TextField createTextField(MockupMultipleColumnElement elem, ArrayList<LinkElem> links) {
		
		TextField textField = ef.createTextField();
		textField.setId(elem.getId());
		textField.setName(elem.getName());
		HashMap<String, Object> properties = elem.getProperties();
		
		if (properties.containsKey("fontSize")) {
			textField.setFontSize((String) properties.get("fontSize")); 
		}
		
		if (properties.containsKey("value")) {
			textField.setTextFieldText((String)elem.getProperties().get("value"));
		}
		
		if(elem.getEvents() != null){
			for(NavigationEvent navEvent: elem.getEvents()){
				
				ViewElementEvent event = f.createViewElementEvent();
				NavigationFlow nf = f.createNavigationFlow();
				
				nf.setSrcInteractionFlowElement(event);
				event.getNavigationFlows().add(nf);
				textField.getViewElementEvents().add(event);
				LinkElem le = new LinkElem();
				le.setId(navEvent.getLink());
				le.setNavigationFlow(nf);
				
				links.add(le);
				
			}
		}
	    
		return textField;
		
	}
	
	/**
	 * Creates a ViewContainer based on a MockupElement.
	 * 
	 * @param elem - MockupElement with all the relevant info.
	 * @return     - ViewContainer object.
	 */
	public ViewContainer createViewContainer(MockupGeneralElement elem, ArrayList<LinkElem> links) {
		
		ViewContainer vc = f.createViewContainer();
		vc.setId(elem.getId());
		vc.setName(elem.getName());
		vc.setIsXor(elem.getType().equals("tabs"));
		
		if (elem.getEvents() != null){
			
			for(NavigationEvent navEvent: elem.getEvents()){
				ViewElementEvent event = f.createViewElementEvent();
				NavigationFlow nf = f.createNavigationFlow();
				
				nf.setSrcInteractionFlowElement(event);
				event.getNavigationFlows().add(nf);
				vc.getViewElementEvents().add(event);
				LinkElem le = new LinkElem();
				le.setId(navEvent.getLink());
				le.setNavigationFlow(nf);
				
				links.add(le);
				
			}
		
		}
		
		return vc;
		
	}
	
	/**
	 * Creates a ViewElement based on a MockupElement.
	 * 
	 * @param elem - MockupElement with all the relevant info.
	 * @return     - ViewElement object.
	 */
	public ViewElement createViewElement(MockupGeneralElement elem, ArrayList<LinkElem> links) {
		
		ViewElement ve = f.createViewElement();
		ve.setId(elem.getId());
		ve.setName(elem.getName());
		
		if (elem.getEvents() != null) {
			for (NavigationEvent navEvent: elem.getEvents()) {
				
				ViewElementEvent event = f.createViewElementEvent();
				NavigationFlow nf = f.createNavigationFlow();
				
				nf.setSrcInteractionFlowElement(event);
				event.getNavigationFlows().add(nf);
				ve.getViewElementEvents().add(event);
				LinkElem le = new LinkElem();
				le.setId(navEvent.getLink());
				le.setNavigationFlow(nf);
				
				links.add(le);
				
			}
		}
		
		return ve;
		
	}
	
	/**
	 * Creates a SubmitEvent based on a MockupElement.
	 * 
	 * @param elem - MockupElement with all the relevant info.
	 * @return     - SubmitEvent object.
	 */
	public SubmitEvent createSubmitEvent(MockupGeneralElement elem) {
		
		SubmitEvent ve = ef.createSubmitEvent();
		ve.setId(elem.getId());
		ve.setName(elem.getName());
		// TODO: add remaining properties.
		
		return ve;
		
	}

	/**
	 * Creates a SelectEvent based on a MockupElement.
	 * 
	 * @param elem - MockupElement with all the relevant info.
	 * @return     - SelectEvent object.
	 */
	public static SelectEvent createSelectEvent(MockupGeneralElement elem) {
		
		SelectEvent se = ef.createSelectEvent();
		se.setId(elem.getId());
		se.setName(elem.getName());

		return se;
		
	}
	
	/**
	 * Creates a SimpleField based on a MockupElement.
	 * 
	 * @param elem - MockupElement with all the relevant info.
	 * @return     - SimpleField object.
	 */
	public SimpleField createSimpleField(MockupGeneralElement elem) {
		
		SimpleField sf = ef.createSimpleField();
		sf.setId(elem.getId());
		sf.setName(elem.getName());
		
		// TODO: add remaining properties.
		HashMap<String, Object> properties = elem.getProperties();
		
		if (properties.containsKey("placeholder")) {
			// sf.setPlaceholder((String) properties.get("placeholder")); 
		}
		
		if (properties.containsKey("label")) {
			// sf.setLabel((String)elem.getProperties().get("label"));
		}
		
		return sf;
		
	}

	/**
	 * Creates a Image based on a MockupElement.
	 * 
	 * @param elem - MockupElement with all the relevant info.
	 * @return     - Image object.
	 */
	public Image createImage(MockupGeneralElement elem, ArrayList<LinkElem> links) {
		
		Image img = ef.createImage();
		img.setId(elem.getId());
		img.setName(elem.getName());
		HashMap<String, Object> properties = elem.getProperties();
		
		if (properties.containsKey("align")) {
			img.setAlign((String) properties.get("align")); 
		}
		
		if (properties.containsKey("url")) {
			img.setUrl((String) properties.get("url"));
		}
		
		if (properties.containsKey("width")) {
			img.setWidth(Double.parseDouble((String) elem.getProperties().get("width"))); 
		}
		
		if (elem.getEvents() != null) {
			for (NavigationEvent navEvent: elem.getEvents()) {
				
				ViewElementEvent event = f.createViewElementEvent();
				NavigationFlow nf = f.createNavigationFlow();
				
				nf.setSrcInteractionFlowElement(event);
				event.getNavigationFlows().add(nf);
				img.getViewElementEvents().add(event);
				LinkElem le = new LinkElem();
				le.setId(navEvent.getLink());
				le.setNavigationFlow(nf);
				
				links.add(le);
				
			}
		}	
		
		return img;
		
	}
	/**
	 * Creates a Video based on a MockupElement.
	 * 
	 * @param elem - MockupElement with all the relevant info.
	 * @return     - Video object.
	 */
	public Video createVideo(MockupGeneralElement elem, ArrayList<LinkElem> links) {
		
		Video vid = ef.createVideo();
		vid.setId(elem.getId());
		vid.setName(elem.getName());
		HashMap<String, Object> properties = elem.getProperties();
		
		if (properties.containsKey("align")) {
			vid.setAlign((String) properties.get("align")); 
		}
		
		if (properties.containsKey("url")) {
			String url = (String)elem.getProperties().get("url");
			if(url.contains("watch?v=")){
				url = url.replace("watch?v=", "embed/");
			}
			vid.setUrl(url);
		}
		
		if (properties.containsKey("height")) {
			vid.setHeight(Double.parseDouble((String) elem.getProperties().get("height")));
		}
		
		if (properties.containsKey("width")) {
			vid.setWidth(Double.parseDouble((String) elem.getProperties().get("width")));
		}
		
		if (elem.getEvents() != null) {
			for (NavigationEvent navEvent: elem.getEvents()) {
				
				ViewElementEvent event = f.createViewElementEvent();
				NavigationFlow nf = f.createNavigationFlow();
				
				nf.setSrcInteractionFlowElement(event);
				event.getNavigationFlows().add(nf);
				vid.getViewElementEvents().add(event);
				LinkElem le = new LinkElem();
				le.setId(navEvent.getLink());
				le.setNavigationFlow(nf);
				
				links.add(le);
				
			}
		}	
		
		return vid;
		
	}
	
//	public DomainConcept createDomainConcept(DomainClass domainClass) {
//
//		UMLDomainConcept dc = f.createUMLDomainConcept();
//		dc.setId(domainClass.getName() + "Id");
//		dc.setName(domainClass.getName());
//		org.eclipse.uml2.uml.Class c = umlf.createClass();
//		c.setName(domainClass.getName());		
//		for (DomainAttribute da : domainClass.getListAttribute()) {
//			DomainProperty prop = da.getProperties();
//			PrimitiveType pt = tf.getPrimitiveType(prop.getTipo());
//			c.createOwnedAttribute(prop.getNombre(), pt);
//		}
//		dc.setClassifier(c);
//		return dc;
//	}
	
	public org.eclipse.uml2.uml.Class createClass(DomainClass domainClass, DomainModel domainModel) {
		org.eclipse.uml2.uml.Class c = umlf.createClass();
		c.setName(domainClass.getName());
		
		UMLDomainConcept umldc = f.createUMLDomainConcept();
		umldc.setId(domainClass.getId());
		umldc.setName(domainClass.getName());
		umldc.setClassifier(c);
		domainModel.getElements().add(umldc);
		
		for (DomainAttribute da: domainClass.getListAttribute()) {
			PrimitiveType pt = tf.getPrimitiveType(da.getType());
			Property p = c.createOwnedAttribute(da.getName(), pt);
			UMLStructuralFeature umlsf = f.createUMLStructuralFeature();
			umlsf.setStructuralFeature(p);
			umlsf.setId(da.getId());
			umlsf.setName(da.getName());
			domainModel.getElements().add(umlsf);
			mapAttributes.put(domainClass.getName() + "_" + da.getName(), umlsf);
		}
		for (DomainOperation oper: domainClass.getListOperation()) {
			Operation bf = umlf.createOperation();
			bf.setName(oper.getName());
			if (oper.getRetorno() != null) {
				PrimitiveType pt = umlf.createPrimitiveType();
				pt.setName(oper.getRetorno());
				bf.createReturnResult(null, pt);
			}
			for (Parameter p: oper.getListParameter()) {
				PrimitiveType pt = umlf.createPrimitiveType();
				pt.setName(p.getTipo());
				bf.createOwnedParameter(p.getName(), pt);
			}
			c.getAllOperations().add(bf);
			UMLBehavioralFeature umlbf = f.createUMLBehavioralFeature();
			umlbf.setBehavioralFeature(bf);
			umlbf.setId(oper.getId());
			umlbf.setName(oper.getName());
			domainModel.getElements().add(umlbf);
			mapOperations.put(oper.getName(), umlbf);
		}
		
		mapClass.put(umldc.getName(), umldc);
		return c;
	}
	
	public Association createAssociation(DomainRelationship domainRelationship, Map<String, org.eclipse.uml2.uml.Class> mapClassifiers) {
		Association a = umlf.createAssociation();
		for (DomainRelationshipEnd dre: domainRelationship.getRelationsEnd()) {
			org.eclipse.uml2.uml.Class c = mapClassifiers.get(dre.getNameClass());
			Property p = umlf.createProperty();
			p.setType(c);
			p.setUpper(dre.getCardUpper());
			p.setLower(dre.getCardLower());
			a.getMemberEnds().add(p);
			c.getAssociations().add(a);
		}
		return a;
	}
	
	public Form createForm(MockupGeneralElement elem, ArrayList<LinkElem> links) {
		
		Form form = ef.createForm();
		form.setId(elem.getId());
		form.setName(elem.getName());
		HashMap<String, Object> properties = elem.getProperties();
		ParameterBindingGroup paramBindGroup = f.createParameterBindingGroup();
		
		if (properties.containsKey("attributes")) {
			for (LinkedTreeMap<String, Object> a: (ArrayList<LinkedTreeMap<String, Object>>)properties.get("attributes")) {
				if (((String)a.get("type")).equals("domain_attribute")) {
					String nombre = (String)((LinkedTreeMap<String, Object>)a.get("properties")).get("nombre");
					String tipo = (String)((LinkedTreeMap<String, Object>)a.get("properties")).get("tipo");
					SimpleField sf = ef.createSimpleField();
					sf.setId(java.util.UUID.randomUUID().toString()); // autogenerar uno
					sf.setName(nombre);
					form.getViewComponentParts().add(sf);
					ParameterBinding pb = f.createParameterBinding();					
					pb.setId(java.util.UUID.randomUUID().toString());
					ifml.core.Parameter sp = f.createParameter();
					sp.setId(java.util.UUID.randomUUID().toString());										
					sp.setName(sf.getName() + "_source_parameter");
					sp.setKind(ParameterKind.INPUT);
					pb.setSourceParameter(sp);
					ifml.core.Parameter tp = f.createParameter();
					tp.setId(java.util.UUID.randomUUID().toString());										
					tp.setName(sf.getName() + "_target_parameter");
					tp.setKind(ParameterKind.OUTPUT);
					pb.setTargetParameter(tp);
					paramBindGroup.getParameterBindings().add(pb);
					
				} else if (((String)a.get("type")).equals("association")) {
					String nombre = (String)((LinkedTreeMap<String, Object>)a.get("properties")).get("nombre");
					String tipoClass = (String)((LinkedTreeMap<String, Object>)a.get("properties")).get("clase");
					DataBinding db = f.createDataBinding();
					db.setId(java.util.UUID.randomUUID().toString());
					db.setName(tipoClass + "_Name");
					db.setDomainConcept(mapClass.get(tipoClass));
					SelectionField sf = ef.createSelectionField();
					sf.setIsMultiSelection(false);
					sf.setId(java.util.UUID.randomUUID().toString()); // autogenerar uno
					sf.setName(nombre);
					sf.getSubViewComponentParts().add(db);
					form.getViewComponentParts().add(sf);
					ParameterBinding pb = f.createParameterBinding();					
					pb.setId(java.util.UUID.randomUUID().toString());
					ifml.core.Parameter sp = f.createParameter();
					sp.setId(java.util.UUID.randomUUID().toString());										
					sp.setName(sf.getName() + "_source_parameter");
					sp.setKind(ParameterKind.INPUT);
					pb.setSourceParameter(sp);
					ifml.core.Parameter tp = f.createParameter();
					tp.setId(java.util.UUID.randomUUID().toString());										
					tp.setName(sf.getName() + "_target_parameter");
					tp.setKind(ParameterKind.OUTPUT);
					pb.setTargetParameter(tp);	
					paramBindGroup.getParameterBindings().add(pb);
					listDataBinding.add(db);
				}
//				form.getViewComponentParts().add(va);
			}
			
		}
		
		if(elem.getEvents() != null && elem.getEvents().get(0) != null) {
			
			SubmitEvent se = ef.createSubmitEvent();
			se.setId(elem.getEvents().get(0).getLink());
			se.setName(elem.getName());
			listSubmitEvent.add(se);
			
			// Creo el action
			Action action = f.createAction();
			action.setId(java.util.UUID.randomUUID().toString());
			action.setName(elem.getName() + "_action");
			DynamicBehavior db = f.createDynamicBehavior();
			db.setId(java.util.UUID.randomUUID().toString());
			db.setName(elem.getName() + "_dynamicBehaviour");
			db.setBehavioralFeatureConcept(mapOperations.get(elem.getEvents().get(0).getBehaviour()));
			action.setDynamicBehavior(db);
			
			NavigationFlow nf = f.createNavigationFlow();
			nf.setId(java.util.UUID.randomUUID().toString());
			nf.setSrcInteractionFlowElement(form);
			nf.setTrgtInteractionFlowElement(action);
			nf.setParameterBindingGroup(paramBindGroup);
			se.getNavigationFlows().add(nf);
			form.getSubmitEvent().add(se);
			
		
			//-----------------------------------------------
								
			ActionEvent actionEvent = f.createActionEvent();
			actionEvent.setId(java.util.UUID.randomUUID().toString());
			actionEvent.setName(elem.getName() + "_actionEvent");
			NavigationFlow nv_salida = f.createNavigationFlow();
			nv_salida.setId(java.util.UUID.randomUUID().toString());
			nv_salida.setSrcInteractionFlowElement(actionEvent);
						
			ParameterBindingGroup pbg_salida = f.createParameterBindingGroup();
			pbg_salida.setId(java.util.UUID.randomUUID().toString());			
			
			ParameterBinding pb_salida = f.createParameterBinding();
			pb_salida.setId(java.util.UUID.randomUUID().toString());
			
			ifml.core.Parameter sp_salida = f.createParameter();			
			sp_salida.setId(java.util.UUID.randomUUID().toString());										
			sp_salida.setName(action.getName() + "_source_parameter");
			sp_salida.setKind(ParameterKind.INPUT);
			pb_salida.setSourceParameter(sp_salida);
			
			ifml.core.Parameter tp_salida = f.createParameter();			
			tp_salida.setId(java.util.UUID.randomUUID().toString());										
			tp_salida.setName(action.getName() + "_target_parameter");
			tp_salida.setKind(ParameterKind.OUTPUT);
			pb_salida.setTargetParameter(tp_salida);
			
			pbg_salida.getParameterBindings().add(pb_salida);			
			nv_salida.setParameterBindingGroup(pbg_salida);
			actionEvent.getNavigationFlows().add(nv_salida);
						
			LinkElem le = new LinkElem();
			le.setId(elem.getEvents().get(0).getLink());
			le.setNavigationFlow(nv_salida);
			links.add(le);
			
			action.getActionEvents().add(actionEvent);
								
		}
		
		return form;
		
	}
	
	public Details createDetails(MockupGeneralElement elem) {
		HashMap<String, Object> properties = elem.getProperties();
		Details details = ef.createDetails();
		details.setId(elem.getId());
		details.setName(elem.getName());
		String entity = (String)properties.get("entity");
		DataBinding db = f.createDataBinding();
		db.setId(java.util.UUID.randomUUID().toString());
		db.setName(entity + "_databinding");
		DomainConcept dc = mapClass.get(entity); 
		db.setDomainConcept(dc);
		String conditionalExp = properties.get("conditionalExpression").toString();
		if(!conditionalExp.equals("")){
			ConditionalExpression ce = f.createConditionalExpression();
			ce.setId(java.util.UUID.randomUUID().toString());
			ce.setName(entity + "_conditionalExpression"); 
			ce.setLanguage("OCL");
			ce.setBody(conditionalExp);
			db.getConditionalExpression().add(ce);			
		}
											
		if (properties.containsKey("attributes")) {
			for (LinkedTreeMap<String, Object> a: (ArrayList<LinkedTreeMap<String, Object>>)properties.get("attributes")) {						
				if(a.get("type").equals("association")){														
					LinkedTreeMap<String, Object> props = (LinkedTreeMap<String, Object>)a.get("properties");
					String clase = (String)(props.get("clase"));
					DataBinding dba = f.createDataBinding();
					dba.setName(clase + "_dataBinding");
					UMLDomainConcept dca = (UMLDomainConcept)mapClass.get(clase);
					dba.setDomainConcept(dca);
					for (Property p: dca.getClassifier().getAttributes()) {
						VisualizationAttribute va = f.createVisualizationAttribute();						
						va.setFeatureConcept(mapAttributes.get(dca.getName() + "_" + p.getName()));
						va.setId(va.getFeatureConcept().getId());
						va.setName(va.getFeatureConcept().getName());
						dba.getVisualizationAttributes().add(va);								
					}	
					details.getViewComponentParts().add(dba);
					listDataBinding.add(dba);
				}
				else{
					VisualizationAttribute va = f.createVisualizationAttribute();						
					va.setFeatureConcept(mapAttributes.get(dc.getName() + "_" + (String)(((LinkedTreeMap<String, Object>)a.get("properties")).get("nombre"))));
					va.setId(va.getFeatureConcept().getId());
					va.setName(va.getFeatureConcept().getName());
					db.getVisualizationAttributes().add(va);	
					listDataBinding.add(db);
				}																								
			}
			
		}
		details.getViewComponentParts().add(db);										
		return details;
	}

	public ArrayList<DataBinding> getListDataBinding() {
		return listDataBinding;
	}
	
	public ArrayList<SubmitEvent> getListSubmitEvent() {
		return listSubmitEvent;
	}


	
}
