/**
 */
package ifml.extensions.impl;

import ifml.core.impl.ViewElementImpl;

import ifml.extensions.Button;
import ifml.extensions.ExtensionsPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Button</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link ifml.extensions.impl.ButtonImpl#getButtonText <em>Button Text</em>}</li>
 *   <li>{@link ifml.extensions.impl.ButtonImpl#getAlign <em>Align</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ButtonImpl extends ViewElementImpl implements Button {
	/**
	 * The default value of the '{@link #getButtonText() <em>Button Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getButtonText()
	 * @generated
	 * @ordered
	 */
	protected static final String BUTTON_TEXT_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getButtonText() <em>Button Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getButtonText()
	 * @generated
	 * @ordered
	 */
	protected String buttonText = BUTTON_TEXT_EDEFAULT;

	/**
	 * The default value of the '{@link #getAlign() <em>Align</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlign()
	 * @generated
	 * @ordered
	 */
	protected static final String ALIGN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAlign() <em>Align</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlign()
	 * @generated
	 * @ordered
	 */
	protected String align = ALIGN_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ButtonImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExtensionsPackage.Literals.BUTTON;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getButtonText() {
		return buttonText;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setButtonText(String newButtonText) {
		String oldButtonText = buttonText;
		buttonText = newButtonText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExtensionsPackage.BUTTON__BUTTON_TEXT, oldButtonText, buttonText));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAlign() {
		return align;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAlign(String newAlign) {
		String oldAlign = align;
		align = newAlign;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExtensionsPackage.BUTTON__ALIGN, oldAlign, align));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ExtensionsPackage.BUTTON__BUTTON_TEXT:
				return getButtonText();
			case ExtensionsPackage.BUTTON__ALIGN:
				return getAlign();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ExtensionsPackage.BUTTON__BUTTON_TEXT:
				setButtonText((String)newValue);
				return;
			case ExtensionsPackage.BUTTON__ALIGN:
				setAlign((String)newValue);
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
			case ExtensionsPackage.BUTTON__BUTTON_TEXT:
				setButtonText(BUTTON_TEXT_EDEFAULT);
				return;
			case ExtensionsPackage.BUTTON__ALIGN:
				setAlign(ALIGN_EDEFAULT);
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
			case ExtensionsPackage.BUTTON__BUTTON_TEXT:
				return BUTTON_TEXT_EDEFAULT == null ? buttonText != null : !BUTTON_TEXT_EDEFAULT.equals(buttonText);
			case ExtensionsPackage.BUTTON__ALIGN:
				return ALIGN_EDEFAULT == null ? align != null : !ALIGN_EDEFAULT.equals(align);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (ButtonText: ");
		result.append(buttonText);
		result.append(", Align: ");
		result.append(align);
		result.append(')');
		return result.toString();
	}

} //ButtonImpl
