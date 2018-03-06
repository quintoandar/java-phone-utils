package br.com.quintoandar.phone.utils;

import com.google.common.base.Strings;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

/**
 * This class parses th string containing a phone number in the format:
 * (XX) BYYYZZZZE
 * If XX is 11 AND B is 9, then E must be in the string. Otherwise, the
 * parser returns false.
 * If XX is NOT 11, then E must nor exist in the string.
 *
 * @author pericles
 *
 */
public class BRPhoneParser {

  /**
   * Deprecated: this method does not validate the telephone number correctly, if phoneNumber has only 1 digit,
   * it will return false. However, if it has more than one, it will return true.
   * <br>
   * Use {@link BRPhoneParser#isValidPhoneNumber(String, boolean)} instead. It validates the number, including
   * the new 9-digit.
   * <br>
   * Parser method
   * @param phoneNumber - Phone String
   * @param notNullNumber - Flag to tell parser if the phone must be
   * not null or not (if it is a primary number or not)
   * @return true or false
   */
  @Deprecated
  public static boolean checkPhoneNumber (String phoneNumber, boolean notNullNumber) {
    if (notNullNumber) {
      if (Strings.isNullOrEmpty(phoneNumber)) {
        return false;
      }
    }

    if(!Strings.isNullOrEmpty(phoneNumber)){
      PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
      try {
        PhoneNumber pnumber =  phoneUtil.parse(phoneNumber, "BR");
        return pnumber != null;
      } catch (NumberParseException e) {
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  /**
   * Validates if the phone string is a valid phone number, includes the validation
   * for the new 9-digit (on all states where it's applicable).
   * @see PhoneNumberUtil#isValidNumber(PhoneNumber)
   *
   * @param phoneNumber - Phone string
   * @param notNullNumber - Flag to tell parser if the phone must be
   * not null or not (if it is a primary number or not)
   * @return
   */
  public static boolean isValidPhoneNumber(String phoneNumber, boolean notNullNumber) {
    if (notNullNumber) {
      if (Strings.isNullOrEmpty(phoneNumber)) {
        return false;
      }
    }

    if(! Strings.isNullOrEmpty(phoneNumber)){
      PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
      try {
        PhoneNumber pnumber =  phoneUtil.parse(phoneNumber, "BR");
        return pnumber != null && phoneUtil.isValidNumber(pnumber);
      } catch (NumberParseException e) {
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }
}