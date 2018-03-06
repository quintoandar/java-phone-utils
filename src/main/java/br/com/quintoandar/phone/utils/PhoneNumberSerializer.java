package br.com.quintoandar.phone.utils;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class PhoneNumberSerializer extends JsonSerializer<String> {

  protected PhoneNumberUtil phoneUtil;

  public PhoneNumberSerializer() {
    phoneUtil = PhoneNumberUtil.getInstance();
  }

  public String writeAsPhoneNumber(String src) throws NumberParseException{
    if (src != null) {
      PhoneNumber phonNumb;

      phonNumb = phoneUtil.parse(src, "BR");
      String fmt = phoneUtil.format(phonNumb, PhoneNumberFormat.INTERNATIONAL);
      if (fmt.startsWith("+55")) {
        fmt = phoneUtil.format(phonNumb, PhoneNumberFormat.NATIONAL);
      }
      return fmt;
    }
    return src;
  }

  public String parseFromPhoneNumber(String src) 
      throws NumberParseException, NumberFormatException {
    PhoneNumber phonNumb = phoneUtil.parse(src, "BR");
    return phoneUtil.format(phonNumb, PhoneNumberFormat.E164);
  }

  @Override
  public void serialize(String src, JsonGenerator gen, SerializerProvider prov)
      throws IOException, JsonProcessingException {
    try {
      gen.writeObject(writeAsPhoneNumber(src));
    } catch (NumberParseException e) {
      e.printStackTrace();
    }
  }
}
