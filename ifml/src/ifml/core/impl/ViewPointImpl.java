/**
 */
package ifml.core.impl;

import ifml.core.Context;
import ifml.core.CorePackage;
import ifml.core.InteractionFlowModelElement;
import ifml.core.ViewPoint;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>View Point</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link ifml.core.impl.ViewPointImpl#getInteractionFlowModelElements <em>Interaction Flow Model Elements</em>}</li>
 *   <li>{@link ifml.core.impl.ViewPointImpl#getContext <em>Context</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ViewPointImpl extends NamedElementImpl implements ViewPoint {
	/**
	 * The cached value of the '{@link #getInteractionFlowModelElements() <em>Interaction Flow Model Elements</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInteractionFlowModelElements()
	 * @generated
	 * @ordered
	 */
	protected EList<InteractionFlowModelElement> interactionFlowModelElements;

	/**
	 * The cached value of the '{@link #getContext() <em>Context</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContext()
	 * @generated
	 * @ordered
	 */
	protected Context context;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ViewPointImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorePackage.Literals.VIEW_POINT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<InteractionFlowModelElement> getInteractionFlowModelElements() {
		if (interactionFlowModelElements == null) {
			interactionFlowModelElements = new EObjectResolvingEList<InteractionFlowModelElement>(InteractionFlowModelElement.class, this, CorePackage.VIEW_POINT__INTERACTION_FLOW_MODEL_ELEMENTS);
		}
		return interactionFlowModelElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Context getContext() {
		if (context != null && context.eIsProxy()) {
			InternalEObject oldContext = (InternalEObject)context;
			context = (Context)eResolveProxy(oldContext);
			if (context != oldContext) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CorePackage.VIEW_POINT__CONTEXT, oldContext, context));
			}
		}
		return context;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Context basicGetContext() {
		return context;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContext(Context newContext) {
		Context oldContext = context;
		context = newContext;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.VIEW_POINT__CONTEXT, oldContext, context));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CorePackage.VIEW_POINT__INTERACTION_FLOW_MODEL_ELEMENTS:
				return getInteractionFlowModelElements();
			case CorePackage.VIEW_POINT__CONTEXT:
				if (resolve) return getContext();
				return basicGetContext();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CorePackage.VIEW_POINT__INTERACTION_FLOW_MODEL_ELEMENTS:
				getInteractionFlowModelElements().clear();
				getInteractionFlowModelElements().addAll((Collection<? extends InteractionFlowModelElement>)newValue);
				return;
			case CorePackage.VIEW_POINT__CONTEXT:
				setContext((Context)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case CorePackage.VIEW_POINT__INTERACTION_FLOW_MODEL_ELEMENTS:
				getInteractionFlowModelElements().clear();
				return;
			case CorePackage.VIEW_POINT__CONTEXT:
				setContext((Context)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CorePackage.VIEW_POINT__INTERACTION_FLOW_MODEL_ELEMENTS:
				return interactionFlowModelElements != null && !interactionFlowModelElements.isEmpty();
			case CorePackage.VIEW_POINT__CONTEXT:
				return context != null;
		}
		return super.eIsSet(featureID);
	}

} //ViewPointImpl
