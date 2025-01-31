/**
 */
package ifml.core.impl;

import ifml.core.ActivationExpression;
import ifml.core.CorePackage;
import ifml.core.ViewComponentPart;
import ifml.core.ViewElementEvent;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>View Component Part</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link ifml.core.impl.ViewComponentPartImpl#getViewElementEvents <em>View Element Events</em>}</li>
 *   <li>{@link ifml.core.impl.ViewComponentPartImpl#getActivationExpression <em>Activation Expression</em>}</li>
 *   <li>{@link ifml.core.impl.ViewComponentPartImpl#getSubViewComponentParts <em>Sub View Component Parts</em>}</li>
 *   <li>{@link ifml.core.impl.ViewComponentPartImpl#getParentViewComponentPart <em>Parent View Component Part</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ViewComponentPartImpl extends InteractionFlowElementImpl implements ViewComponentPart {
	/**
	 * The cached value of the '{@link #getViewElementEvents() <em>View Element Events</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getViewElementEvents()
	 * @generated
	 * @ordered
	 */
	protected EList<ViewElementEvent> viewElementEvents;

	/**
	 * The cached value of the '{@link #getActivationExpression() <em>Activation Expression</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActivationExpression()
	 * @generated
	 * @ordered
	 */
	protected ActivationExpression activationExpression;

	/**
	 * The cached value of the '{@link #getSubViewComponentParts() <em>Sub View Component Parts</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubViewComponentParts()
	 * @generated
	 * @ordered
	 */
	protected EList<ViewComponentPart> subViewComponentParts;

	/**
	 * The cached value of the '{@link #getParentViewComponentPart() <em>Parent View Component Part</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentViewComponentPart()
	 * @generated
	 * @ordered
	 */
	protected ViewComponentPart parentViewComponentPart;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ViewComponentPartImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorePackage.Literals.VIEW_COMPONENT_PART;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ViewElementEvent> getViewElementEvents() {
		if (viewElementEvents == null) {
			viewElementEvents = new EObjectContainmentEList<ViewElementEvent>(ViewElementEvent.class, this, CorePackage.VIEW_COMPONENT_PART__VIEW_ELEMENT_EVENTS);
		}
		return viewElementEvents;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActivationExpression getActivationExpression() {
		if (activationExpression != null && activationExpression.eIsProxy()) {
			InternalEObject oldActivationExpression = (InternalEObject)activationExpression;
			activationExpression = (ActivationExpression)eResolveProxy(oldActivationExpression);
			if (activationExpression != oldActivationExpression) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CorePackage.VIEW_COMPONENT_PART__ACTIVATION_EXPRESSION, oldActivationExpression, activationExpression));
			}
		}
		return activationExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActivationExpression basicGetActivationExpression() {
		return activationExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActivationExpression(ActivationExpression newActivationExpression) {
		ActivationExpression oldActivationExpression = activationExpression;
		activationExpression = newActivationExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.VIEW_COMPONENT_PART__ACTIVATION_EXPRESSION, oldActivationExpression, activationExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ViewComponentPart> getSubViewComponentParts() {
		if (subViewComponentParts == null) {
			subViewComponentParts = new EObjectWithInverseResolvingEList<ViewComponentPart>(ViewComponentPart.class, this, CorePackage.VIEW_COMPONENT_PART__SUB_VIEW_COMPONENT_PARTS, CorePackage.VIEW_COMPONENT_PART__PARENT_VIEW_COMPONENT_PART);
		}
		return subViewComponentParts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ViewComponentPart getParentViewComponentPart() {
		if (parentViewComponentPart != null && parentViewComponentPart.eIsProxy()) {
			InternalEObject oldParentViewComponentPart = (InternalEObject)parentViewComponentPart;
			parentViewComponentPart = (ViewComponentPart)eResolveProxy(oldParentViewComponentPart);
			if (parentViewComponentPart != oldParentViewComponentPart) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CorePackage.VIEW_COMPONENT_PART__PARENT_VIEW_COMPONENT_PART, oldParentViewComponentPart, parentViewComponentPart));
			}
		}
		return parentViewComponentPart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ViewComponentPart basicGetParentViewComponentPart() {
		return parentViewComponentPart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParentViewComponentPart(ViewComponentPart newParentViewComponentPart, NotificationChain msgs) {
		ViewComponentPart oldParentViewComponentPart = parentViewComponentPart;
		parentViewComponentPart = newParentViewComponentPart;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CorePackage.VIEW_COMPONENT_PART__PARENT_VIEW_COMPONENT_PART, oldParentViewComponentPart, newParentViewComponentPart);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentViewComponentPart(ViewComponentPart newParentViewComponentPart) {
		if (newParentViewComponentPart != parentViewComponentPart) {
			NotificationChain msgs = null;
			if (parentViewComponentPart != null)
				msgs = ((InternalEObject)parentViewComponentPart).eInverseRemove(this, CorePackage.VIEW_COMPONENT_PART__SUB_VIEW_COMPONENT_PARTS, ViewComponentPart.class, msgs);
			if (newParentViewComponentPart != null)
				msgs = ((InternalEObject)newParentViewComponentPart).eInverseAdd(this, CorePackage.VIEW_COMPONENT_PART__SUB_VIEW_COMPONENT_PARTS, ViewComponentPart.class, msgs);
			msgs = basicSetParentViewComponentPart(newParentViewComponentPart, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.VIEW_COMPONENT_PART__PARENT_VIEW_COMPONENT_PART, newParentViewComponentPart, newParentViewComponentPart));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CorePackage.VIEW_COMPONENT_PART__SUB_VIEW_COMPONENT_PARTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getSubViewComponentParts()).basicAdd(otherEnd, msgs);
			case CorePackage.VIEW_COMPONENT_PART__PARENT_VIEW_COMPONENT_PART:
				if (parentViewComponentPart != null)
					msgs = ((InternalEObject)parentViewComponentPart).eInverseRemove(this, CorePackage.VIEW_COMPONENT_PART__SUB_VIEW_COMPONENT_PARTS, ViewComponentPart.class, msgs);
				return basicSetParentViewComponentPart((ViewComponentPart)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CorePackage.VIEW_COMPONENT_PART__VIEW_ELEMENT_EVENTS:
				return ((InternalEList<?>)getViewElementEvents()).basicRemove(otherEnd, msgs);
			case CorePackage.VIEW_COMPONENT_PART__SUB_VIEW_COMPONENT_PARTS:
				return ((InternalEList<?>)getSubViewComponentParts()).basicRemove(otherEnd, msgs);
			case CorePackage.VIEW_COMPONENT_PART__PARENT_VIEW_COMPONENT_PART:
				return basicSetParentViewComponentPart(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CorePackage.VIEW_COMPONENT_PART__VIEW_ELEMENT_EVENTS:
				return getViewElementEvents();
			case CorePackage.VIEW_COMPONENT_PART__ACTIVATION_EXPRESSION:
				if (resolve) return getActivationExpression();
				return basicGetActivationExpression();
			case CorePackage.VIEW_COMPONENT_PART__SUB_VIEW_COMPONENT_PARTS:
				return getSubViewComponentParts();
			case CorePackage.VIEW_COMPONENT_PART__PARENT_VIEW_COMPONENT_PART:
				if (resolve) return getParentViewComponentPart();
				return basicGetParentViewComponentPart();
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
			case CorePackage.VIEW_COMPONENT_PART__VIEW_ELEMENT_EVENTS:
				getViewElementEvents().clear();
				getViewElementEvents().addAll((Collection<? extends ViewElementEvent>)newValue);
				return;
			case CorePackage.VIEW_COMPONENT_PART__ACTIVATION_EXPRESSION:
				setActivationExpression((ActivationExpression)newValue);
				return;
			case CorePackage.VIEW_COMPONENT_PART__SUB_VIEW_COMPONENT_PARTS:
				getSubViewComponentParts().clear();
				getSubViewComponentParts().addAll((Collection<? extends ViewComponentPart>)newValue);
				return;
			case CorePackage.VIEW_COMPONENT_PART__PARENT_VIEW_COMPONENT_PART:
				setParentViewComponentPart((ViewComponentPart)newValue);
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
			case CorePackage.VIEW_COMPONENT_PART__VIEW_ELEMENT_EVENTS:
				getViewElementEvents().clear();
				return;
			case CorePackage.VIEW_COMPONENT_PART__ACTIVATION_EXPRESSION:
				setActivationExpression((ActivationExpression)null);
				return;
			case CorePackage.VIEW_COMPONENT_PART__SUB_VIEW_COMPONENT_PARTS:
				getSubViewComponentParts().clear();
				return;
			case CorePackage.VIEW_COMPONENT_PART__PARENT_VIEW_COMPONENT_PART:
				setParentViewComponentPart((ViewComponentPart)null);
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
			case CorePackage.VIEW_COMPONENT_PART__VIEW_ELEMENT_EVENTS:
				return viewElementEvents != null && !viewElementEvents.isEmpty();
			case CorePackage.VIEW_COMPONENT_PART__ACTIVATION_EXPRESSION:
				return activationExpression != null;
			case CorePackage.VIEW_COMPONENT_PART__SUB_VIEW_COMPONENT_PARTS:
				return subViewComponentParts != null && !subViewComponentParts.isEmpty();
			case CorePackage.VIEW_COMPONENT_PART__PARENT_VIEW_COMPONENT_PART:
				return parentViewComponentPart != null;
		}
		return super.eIsSet(featureID);
	}

} //ViewComponentPartImpl
