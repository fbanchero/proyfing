package api.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EcoreFactory;

import com.google.gson.internal.LinkedTreeMap;

import api.classes.DomainAttribute;
import api.classes.DomainClass;
import api.classes.DomainOperation;
import api.classes.DomainRelationshipEnd;
import api.classes.MockupGeneralElement;
import api.classes.MockupMultipleColumnElement;
import api.classes.MockupSingleColumnElement;
import api.classes.NavigationEvent;
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
import ifml.core.SystemEvent;
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
import uml.Association;
import uml.BehavioralFeature;
import uml.Classifier;
import uml.PrimitiveType;
import uml.StructuralFeature;
import uml.UmlFactory;

public class ElementBuilder {
	
    public static CoreFactory f;
    public static ExtensionsFactory ef;
    public static UmlFactory umlf;
	public static TypeFactory tf;
	public static EcoreFactory ecf;
	
	public Map<String, LinkElem> mapLinkElems;
	public Map<String, FeatureConcept> mapAttributes;
	public Map<String, BehavioralFeatureConcept> mapOperations;
	public Map<String, DomainConcept> mapClass;
	public ArrayList<Action> listAction;
	public ArrayList<SystemEvent> listSystemEvent;

	public ElementBuilder(CoreFactory pf, ExtensionsFactory pef, UmlFactory uf) {
		f = pf;
		ef = pef;
		umlf = uf;
		tf = new TypeFactory(umlf);
		listAction = new ArrayList<Action>();
		listSystemEvent = new ArrayList<SystemEvent>();
		mapLinkElems = new HashMap<String, LinkElem>();
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
		
		// Para cada propiedad selectEvent se crea el evento, el navigation flow
		// que va al action asociado y el action con su dynamic beahviour.
		// Se crean los parametros necesarios.
		// Se crea el navigation flow para luego engancharlo con la pagina destino
		ArrayList<String> selectEvents = (ArrayList<String>)elem.getProperties().get("selectEvents");
		if(selectEvents != null) {
			for(String e: selectEvents){
				SelectEvent se = ef.createSelectEvent();
				se.setId(java.util.UUID.randomUUID().toString().substring(0,8));
				se.setName(e);
				
				Action action = f.createAction();
				action.setId(java.util.UUID.randomUUID().toString().substring(0,8));
				action.setName(e + "_action");
				DynamicBehavior db = f.createDynamicBehavior();
				db.setId(java.util.UUID.randomUUID().toString().substring(0,8));
				db.setName(e + "_dynamicBehaviour");
				db.setBehavioralFeatureConcept(mapOperations.get(e));				
				action.setDynamicBehavior(db);
				list.getViewComponentParts().add(db);
				
				ParameterBindingGroup paramBindGroup = f.createParameterBindingGroup();
				paramBindGroup.setId(java.util.UUID.randomUUID().toString().substring(0,8));
				
				ParameterBinding pb = f.createParameterBinding();
				pb.setId(java.util.UUID.randomUUID().toString().substring(0,8));
				
				ifml.core.Parameter sp = f.createParameter();			
				sp.setId(java.util.UUID.randomUUID().toString().substring(0,8).substring(0,8));										
				sp.setName(action.getName() + "_src");
				sp.setKind(ParameterKind.INPUT);
				pb.setSourceParameter(sp);
				list.getParameters().add(sp);
				
				ifml.core.Parameter tp = f.createParameter();			
				tp.setId(java.util.UUID.randomUUID().toString().substring(0,8));										
				tp.setName(action.getName() + "_trg");
				tp.setKind(ParameterKind.OUTPUT);
				pb.setTargetParameter(tp);
				action.getParameters().add(tp);
				
				paramBindGroup.getParameterBindings().add(pb);			
							
				NavigationFlow nf = f.createNavigationFlow();
				nf.setSrcInteractionFlowElement(list);
				nf.setTrgtInteractionFlowElement(action);
				nf.setParameterBindingGroup(paramBindGroup);
				
				se.getNavigationFlows().add(nf);
				
				list.getSelectEvent().add(se);
				
				LinkElem le = new LinkElem();
				le.setId(e);
				le.setNavigationFlow(nf);	
				links.add(le);		
				
				listAction.add(action);
	
			}
		}
		
		
		// Se crean los system events
		ArrayList<LinkedTreeMap<String, Object>> systemEvents = (ArrayList<LinkedTreeMap<String, Object>>)elem.getProperties().get("systemEvents");
		if(systemEvents != null && !systemEvents.isEmpty()) {
			LinkedTreeMap<String, Object> systemEventMap = systemEvents.get(0);
			SystemEvent se = f.createSystemEvent();
			se.setId(java.util.UUID.randomUUID().toString().substring(0,8));
			se.setName((String)systemEventMap.get("event"));
			ConditionalExpression exp = f.createConditionalExpression();
			exp.setLanguage("OCL");
			exp.setBody((String)systemEventMap.get("trigger"));
			se.getTriggeringExpressions().add(exp);
			list.getViewComponentParts().add(exp);	
			listSystemEvent.add(se);
			
			Action action = f.createAction();
			action.setId(java.util.UUID.randomUUID().toString().substring(0,8));
			action.setName(se.getName() + "_action");
			DynamicBehavior db = f.createDynamicBehavior();
			db.setId(java.util.UUID.randomUUID().toString().substring(0,8));
			db.setName(se.getName() + "_dynamicBehaviour");
			db.setBehavioralFeatureConcept(mapOperations.get(se.getName()));				
			action.setDynamicBehavior(db);
			list.getViewComponentParts().add(db);
			
			NavigationFlow nf1 = f.createNavigationFlow();
			nf1.setSrcInteractionFlowElement(se);
			nf1.setTrgtInteractionFlowElement(action);
//			nf.setParameterBindingGroup(paramBindGroup);
			
			se.getNavigationFlows().add(nf1);
			
//			list.getSelectEvent().add(se);
			
			ActionEvent actionEvent = f.createActionEvent();
			actionEvent.setId(java.util.UUID.randomUUID().toString().substring(0,8));
			actionEvent.setName(elem.getName() + "_actionEvent");
			
			NavigationFlow nf2 = f.createNavigationFlow();
			nf2.setId(java.util.UUID.randomUUID().toString().substring(0,8));
			nf2.setSrcInteractionFlowElement(actionEvent);
			nf2.setTrgtInteractionFlowElement(list);
			action.getActionEvents().add(actionEvent);
			listAction.add(action);
			

		}
		
		HashMap<String, Object> properties = elem.getProperties();
		
		// Se crean los databinding asoiados a la tabla
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
							dba.setName(clase + "_db");
							UMLDomainConcept dca = (UMLDomainConcept)mapClass.get(clase);
							dba.setDomainConcept(dca);
							for (StructuralFeature p: dca.getClassifier().getStructuralFeatures()) {
								VisualizationAttribute va = f.createVisualizationAttribute();						
								va.setFeatureConcept(mapAttributes.get(dca.getName() + "_" + p.getNombre()));
								va.setId(va.getFeatureConcept().getId());
								va.setName(va.getFeatureConcept().getName());
								list.getViewComponentParts().add(va);
								dba.getVisualizationAttributes().add(va);								
							}	
							list.getViewComponentParts().add(dba);
						}
						else{
							VisualizationAttribute va = f.createVisualizationAttribute();							
							va.setFeatureConcept(mapAttributes.get(dc.getName() + "_" + (String)(((LinkedTreeMap<String, Object>)a.get("properties")).get("nombre"))));
							va.setId(va.getFeatureConcept().getId());
							va.setName(va.getFeatureConcept().getName());
							db.getVisualizationAttributes().add(va);
							list.getViewComponentParts().add(va);
						}																								
					}
					
				}
				list.getViewComponentParts().add(db);
				String conditionalExp = (String) properties.get("conditionalExpression");
				if (conditionalExp != null) {
					if(!conditionalExp.equals("")){
						ConditionalExpression ce = f.createConditionalExpression();
						ce.setId(java.util.UUID.randomUUID().toString().substring(0,8));
						ce.setName(entity + "_conditionalExpression"); 
						ce.setLanguage("OCL");
						ce.setBody(conditionalExp);
						db.getConditionalExpression().add(ce);
						list.getViewComponentParts().add(ce);
					}
				}
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
		
		if (mapLinkElems.containsKey(elem.getId())) {
			LinkElem le = mapLinkElems.get(elem.getId());
			for (ParameterBinding pb: le.getParameterBindingGroup().getParameterBindings()) {
				vc.getParameters().add(pb.getTargetParameter());
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
	
	public Classifier createClass(DomainClass domainClass, DomainModel domainModel) {
		
		UMLDomainConcept umldc = (UMLDomainConcept)mapClass.get(domainClass.getName());		
		Classifier c;
		if(umldc == null ){
			c = umlf.createClassifier();
			c.setNombre(domainClass.getName());
			umldc = f.createUMLDomainConcept();
			umldc.setId(domainClass.getId());
			umldc.setName(domainClass.getName());
			umldc.setClassifier(c);
			domainModel.getElements().add(umldc);
		} else {
			c = umldc.getClassifier();
			umldc.setId(domainClass.getId());
		}
		
		// Se crean los atributos de la clase
		for (DomainAttribute da: domainClass.getListAttribute()) {
			PrimitiveType pt = tf.getPrimitiveType(da.getType());
			StructuralFeature p = umlf.createStructuralFeature();
			p.setNombre(da.getName());
			p.setPrimitiveType(pt);
			c.getStructuralFeatures().add(p);
			UMLStructuralFeature umlsf = f.createUMLStructuralFeature();
			umlsf.setStructuralFeature(p);
			umlsf.setId(da.getId());
			umlsf.setName(da.getName());			
			domainModel.getElements().add(umlsf);
			mapAttributes.put(domainClass.getName() + "_" + da.getName(), umlsf);
		}
		
		// Se crean las relaciones de la clase
		for (DomainRelationshipEnd dr: domainClass.getListRelationships()){
			Association a = umlf.createAssociation();
			UMLDomainConcept dest = (UMLDomainConcept)(mapClass.get(dr.getNameClass()));
			if(dest == null)
			{
				Classifier cd = umlf.createClassifier();
				cd.setNombre(dr.getNameClass());
				dest = f.createUMLDomainConcept();
				dest.setName(dr.getNameClass());
				dest.setClassifier(cd);
				domainModel.getElements().add(dest);
				mapClass.put(dr.getNameClass(), dest);					
			}
			a.setNombre(dr.getName());
			a.setMemberEnd(dest.getClassifier());
			a.setCardinality(dr.getCardinality());
			c.getAssociations().add(a);
		}
				
		// Se crean las operaciones de la clase
		for (DomainOperation oper: domainClass.getListOperation()) {
			BehavioralFeature bf = umlf.createBehavioralFeature();
			bf.setNombre(oper.getName());
			c.getBehavioralFeatures().add(bf);
			UMLBehavioralFeature umlbf = f.createUMLBehavioralFeature();
			umlbf.setBehavioralFeature(bf);
			umlbf.setId(oper.getId());
			umlbf.setName(oper.getName());
			domainModel.getElements().add(umlbf);
			mapOperations.put(oper.getId(), umlbf);
		}
		
		
		
		mapClass.put(umldc.getName(), umldc);
		return c;
	}
	
	public Form createForm(MockupGeneralElement elem, ArrayList<LinkElem> links) {
		
		Form form = ef.createForm();
		form.setId(elem.getId());
		form.setName(elem.getName());
		HashMap<String, Object> properties = elem.getProperties();
		ParameterBindingGroup paramBindGroup = f.createParameterBindingGroup();
		
		// Se crean las entradas del formulario, simpleField para los atributos, selectionField para asociaciones,
		// ademas se crean los parametros asociados a cada una de esas entradas para luego utilizarlos en el action
		if (properties.containsKey("attributes")) {
			for (LinkedTreeMap<String, Object> a: (ArrayList<LinkedTreeMap<String, Object>>)properties.get("attributes")) {
				if (((String)a.get("type")).equals("domain_attribute")) {
					String nombre = (String)((LinkedTreeMap<String, Object>)a.get("properties")).get("nombre");
					String tipo = (String)((LinkedTreeMap<String, Object>)a.get("properties")).get("tipo");
					SimpleField sf = ef.createSimpleField();
					sf.setId(java.util.UUID.randomUUID().toString().substring(0,8)); // autogenerar uno
					sf.setName(nombre);
					form.getViewComponentParts().add(sf);
					
					ParameterBinding pb = f.createParameterBinding();					
					pb.setId(java.util.UUID.randomUUID().toString().substring(0,8));
					ifml.core.Parameter sp = f.createParameter();
					sp.setId(java.util.UUID.randomUUID().toString().substring(0,8));										
					sp.setName(sf.getName() + "_src");
					sp.setKind(ParameterKind.INPUT);
					pb.setSourceParameter(sp);
					sf.getParameters().add(sp);
					ifml.core.Parameter tp = f.createParameter();
					tp.setId(java.util.UUID.randomUUID().toString().substring(0,8));										
					tp.setName(sf.getName() + "_trg");
					tp.setKind(ParameterKind.OUTPUT);
					pb.setTargetParameter(tp);
					paramBindGroup.getParameterBindings().add(pb);
					
				} else if (((String)a.get("type")).equals("association")) {
					String nombre = (String)((LinkedTreeMap<String, Object>)a.get("properties")).get("nombre");
					String tipoClass = (String)((LinkedTreeMap<String, Object>)a.get("properties")).get("clase");
					DataBinding db = f.createDataBinding();
					db.setId(java.util.UUID.randomUUID().toString().substring(0,8));
					db.setName(tipoClass + "_db");
					db.setDomainConcept(mapClass.get(tipoClass));
					
					SelectionField sf = ef.createSelectionField();
					sf.setIsMultiSelection(false);
					sf.setId(java.util.UUID.randomUUID().toString().substring(0,8)); // autogenerar uno
					sf.setName(nombre);
					sf.getSubViewComponentParts().add(sf);
					form.getViewComponentParts().add(sf);
					form.getViewComponentParts().add(db);
					
					ParameterBinding pb = f.createParameterBinding();					
					pb.setId(java.util.UUID.randomUUID().toString().substring(0,8));
					ifml.core.Parameter sp = f.createParameter();
					sp.setId(java.util.UUID.randomUUID().toString().substring(0,8));										
					sp.setName(sf.getName() + "_src");
					sp.setKind(ParameterKind.INPUT);
					pb.setSourceParameter(sp);
					sf.getParameters().add(sp);
					ifml.core.Parameter tp = f.createParameter();
					tp.setId(java.util.UUID.randomUUID().toString().substring(0,8));										
					tp.setName(sf.getName() + "_trg");
					tp.setKind(ParameterKind.OUTPUT);
					pb.setTargetParameter(tp);
					paramBindGroup.getParameterBindings().add(pb);
				}
			}
			
		}
		
		if(elem.getEvents() != null && elem.getEvents().get(0) != null  && !elem.getEvents().get(0).getLink().isEmpty()) {
			
			SubmitEvent se = ef.createSubmitEvent();
			se.setId(elem.getEvents().get(0).getLink());
			se.setName(elem.getName());
			
			// Creo el action
			Action action = f.createAction();
			action.setId(java.util.UUID.randomUUID().toString().substring(0,8));
			action.setName(elem.getName() + "_act");
			listAction.add(action);
			DynamicBehavior db = f.createDynamicBehavior();
			db.setId(java.util.UUID.randomUUID().toString().substring(0,8));
			db.setName(elem.getName() + "_dynamicBehaviour");
			db.setBehavioralFeatureConcept(mapOperations.get(elem.getEvents().get(0).getBehaviour()));
			action.setDynamicBehavior(db);
			form.getViewComponentParts().add(db);
			for (ParameterBinding pb: paramBindGroup.getParameterBindings()) {
				ifml.core.Parameter tp = pb.getTargetParameter();
				db.getParameters().add(tp);
				form.getParameters().add(tp);
			}
			NavigationFlow nf = f.createNavigationFlow();
			nf.setId(java.util.UUID.randomUUID().toString().substring(0,8));
			nf.setSrcInteractionFlowElement(se);
			nf.setTrgtInteractionFlowElement(action);
			nf.setParameterBindingGroup(paramBindGroup);
			se.getNavigationFlows().add(nf);
			form.getSubmitEvent().add(se);
			action.getInInteractionFlows().add(nf);
		
			//-----------------------------------------------
								
			ActionEvent actionEvent = f.createActionEvent();
			actionEvent.setId(java.util.UUID.randomUUID().toString().substring(0,8));
			actionEvent.setName(elem.getName() + "_actionEvent");
			NavigationFlow nv_salida = f.createNavigationFlow();
			nv_salida.setId(java.util.UUID.randomUUID().toString().substring(0,8));
			nv_salida.setSrcInteractionFlowElement(actionEvent);
						
			ParameterBindingGroup pbg_salida = f.createParameterBindingGroup();
			pbg_salida.setId(java.util.UUID.randomUUID().toString().substring(0,8));			
			
			ParameterBinding pb_salida = f.createParameterBinding();
			pb_salida.setId(java.util.UUID.randomUUID().toString().substring(0,8));
			
			ifml.core.Parameter sp_salida = f.createParameter();			
			sp_salida.setId(java.util.UUID.randomUUID().toString().substring(0,8));										
			sp_salida.setName(action.getName() + "_src");
			sp_salida.setKind(ParameterKind.INPUT);
			pb_salida.setSourceParameter(sp_salida);
			db.getParameters().add(sp_salida);
			actionEvent.getParameters().add(sp_salida);
			
			ifml.core.Parameter tp_salida = f.createParameter();			
			tp_salida.setId(java.util.UUID.randomUUID().toString().substring(0,8));										
			tp_salida.setName(action.getName() + "_trg");
			tp_salida.setKind(ParameterKind.OUTPUT);
			pb_salida.setTargetParameter(tp_salida);
			
			pbg_salida.getParameterBindings().add(pb_salida);
			nv_salida.setParameterBindingGroup(pbg_salida);
			actionEvent.getNavigationFlows().add(nv_salida);
						
			LinkElem le = new LinkElem();
			le.setId(elem.getEvents().get(0).getLink());
			mapLinkElems.put(elem.getEvents().get(0).getLink(), le);
			le.setNavigationFlow(nv_salida);
			le.setParameterBindingGroup(pbg_salida);
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
		db.setId(java.util.UUID.randomUUID().toString().substring(0,8));
		db.setName(entity + "_db");
		DomainConcept dc = mapClass.get(entity); 
		db.setDomainConcept(dc);
		String conditionalExp = properties.get("conditionalExpression").toString();
		
		// Condicion sobre el details
		if(conditionalExp != null && !conditionalExp.equals("")){
			ConditionalExpression ce = f.createConditionalExpression();
			ce.setId(java.util.UUID.randomUUID().toString().substring(0,8));
			ce.setName(entity + "_conditionalExpression"); 
			ce.setLanguage("OCL");
			ce.setBody(conditionalExp);
			db.getConditionalExpression().add(ce);
			details.getViewComponentParts().add(ce);
		}
									
		// Artibutos mostrados
		if (properties.containsKey("attributes")) {
			for (LinkedTreeMap<String, Object> a: (ArrayList<LinkedTreeMap<String, Object>>)properties.get("attributes")) {						
				if(a.get("type").equals("association")){														
					LinkedTreeMap<String, Object> props = (LinkedTreeMap<String, Object>)a.get("properties");
					String clase = (String)(props.get("clase"));
					DataBinding dba = f.createDataBinding();
					dba.setName(clase + "_db");
					UMLDomainConcept dca = (UMLDomainConcept)mapClass.get(clase);
					dba.setDomainConcept(dca);
					for (BehavioralFeature p: dca.getClassifier().getBehavioralFeatures()) {
						VisualizationAttribute va = f.createVisualizationAttribute();						
						va.setFeatureConcept(mapAttributes.get(dca.getName() + "_" + p.getNombre()));
						va.setId(va.getFeatureConcept().getId());
						va.setName(va.getFeatureConcept().getName());
						details.getViewComponentParts().add(va);
						dba.getVisualizationAttributes().add(va);								
					}	
					details.getViewComponentParts().add(dba);
				}
				else{
					VisualizationAttribute va = f.createVisualizationAttribute();						
					va.setFeatureConcept(mapAttributes.get(dc.getName() + "_" + (String)(((LinkedTreeMap<String, Object>)a.get("properties")).get("nombre"))));
					va.setId(va.getFeatureConcept().getId());
					va.setName(va.getFeatureConcept().getName());
					db.getVisualizationAttributes().add(va);
					details.getViewComponentParts().add(va);
				}																								
			}
			
		}
		details.getViewComponentParts().add(db);
		return details;
	}

	public ArrayList<Action> getListAction() {
		return listAction;
	}
	
	public ArrayList<SystemEvent> getListSystemEvent() {
		return listSystemEvent;
	}


	
}
