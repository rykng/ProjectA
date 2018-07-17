package com.qang.util;

import java.util.Random;
/**
 * CreditCard goodies
 * @author Richard Ng
 *
 */

public class CreditCardUtils {
      public final static int s_VISA = 0;
      public final static int s_MASTERCARD = 1;
      public final static int s_AMEX = 2;
      public final static int s_DINERSCLUB = 3;
      public final static int s_DISCOVER = 4;
      public final static int s_UNKNOWN = 99;

      /**
       *
       * @param binNum
       * @return
       */
      public static String generateValidCCNumByBinPrefix(String binNum) {
        final int DIGITS_TO_FILL = 4;
        int ccType = findType(binNum);
            int length = getCCLengthByType(ccType);
            int needToFill = length - binNum.length()- DIGITS_TO_FILL; //reserve four digits for 0000
            StringBuffer buf = new StringBuffer();
            buf.append(binNum);
          String middle = needToFill(needToFill);
          buf.append(middle);
          boolean done = false;
          int count = 0;
          String cc = "";

          while (!done) {
             String end = numberToStringFiller(count, DIGITS_TO_FILL);
             cc = buf.toString() + end;
             boolean validate = CreditCardUtils.validate(cc);
             if (validate) {
               buf.append(end);
               done = true;
             }
             count++;
          }
          return buf.toString();
      }

      /**
       *
       * @param binNum
       * @return
       */
      public static String generateInvalidCCNumByBinPrefix(String binNum) {
        final int DIGITS_TO_FILL = 4;
        int ccType = findType(binNum);
            int length = getCCLengthByType(ccType);
            int needToFill = length - binNum.length()- DIGITS_TO_FILL; //reserve four digits for 0000
            StringBuffer buf = new StringBuffer();
            buf.append(binNum);
          String middle = needToFill(needToFill);
          buf.append(middle);
          boolean done = false;
          int count = 0;
          String cc = "";
          while (!done) {
             String end = numberToStringFiller(count, DIGITS_TO_FILL);
             cc = buf.toString() + end;
             boolean validate = CreditCardUtils.validate(cc);
             if (!validate) {
               buf.append(end);
               done = true;
             }
             count++;
          }
          return buf.toString();
      }

      public static String needToFill(int n) {
            StringBuffer buffer = new StringBuffer();
            Random digitGen = new Random();
            for (int i=0;i<n;i++) {
                  int d = digitGen.nextInt(10);
                  buffer.append(d);
            }
            return buffer.toString();
      }

      /*
       * if n = 1, length = 5, then output should be like this : "00001"
       * if n = 32 , length = 4, then output should be like this : "0032"
       */
      public static String numberToStringFiller(int n, int length) {
            StringBuffer buf = new StringBuffer();
          buf.append(n);
          int gap = length - buf.length();
          if (gap > 0) {
            for (int i=0;i<gap;i++) {
                  buf.insert(0, 0);
            }
          }
          return buf.toString();
      }

      /**
       * Generate Valid CC Num by Type.
       * @param cardType
       * @return
       */
      public static String generateValidCCNumByType(int ccType) {
            final int DIGITS_TO_FILL = 6;
            String prefix = getCreditCardPrefixByType(ccType);
            int length = getCCLengthByType(ccType);
            int needToFill = length - prefix.length()- DIGITS_TO_FILL; //reserve four digits for 0000
            StringBuffer buf = new StringBuffer();
            buf.append(prefix);
          String middle = needToFill(needToFill);
          buf.append(middle);
          boolean done = false;
          int count = 0;
          String cc = "";
          while (!done) {
             String end = numberToStringFiller(count, DIGITS_TO_FILL);
             cc = buf.toString() + end;
             boolean validate = CreditCardUtils.validate(cc);
             if (validate) {
               buf.append(end);
               done = true;
             }
             count++;
          }
          return buf.toString();
      }

      /**
       * Generate invalid CC Num by Type.
       * @param cardType
       * @return
       */
      public static String generateInvalidCCNumByType(int ccType) {
            final int DIGITS_TO_FILL = 8;
            String prefix = getCreditCardPrefixByType(ccType);
            int length = getCCLengthByType(ccType);
            int needToFill = length - prefix.length()- DIGITS_TO_FILL; //reserve four digits for 0000
            StringBuffer buf = new StringBuffer();
            buf.append(prefix);
          String middle = needToFill(needToFill);
          buf.append(middle);
          boolean done = false;
          int count = 0;
          String cc = "";
          while (!done) {
             String end = numberToStringFiller(count, DIGITS_TO_FILL);
             cc = buf.toString() + end;
             boolean validate = CreditCardUtils.validate(cc);
             if (!validate) {
               buf.append(end);
               done = true;
             }
             count++;
          }
          return buf.toString();
      }

      /**
       *
       * @param ccNum CreditCard Number
       * @return Type
       */
      public static int findType(String ccNum) {
            if (ccNum.startsWith("30") || ccNum.startsWith("36") || ccNum.startsWith("38")) {
                  return s_DINERSCLUB;
            }
            else if (ccNum.startsWith("6011")) {
                  return s_DISCOVER;
            }
            else if (ccNum.startsWith("4")) {
                  return s_VISA;
            }
            else if (ccNum.startsWith("34") || ccNum.startsWith("37")) {
                  return s_AMEX;
            }
            else if (ccNum.startsWith("51") || ccNum.startsWith("52") || ccNum.startsWith("53") || ccNum.startsWith("54") || ccNum.startsWith("55")) {
                  return s_MASTERCARD;
            }
            else {
                  return s_UNKNOWN;
            }
      }

      /**
       *
       * @param ccNum CreditCard Number
       * @return Type
       */
      public static String getCreditCardPrefixByType(int ccType) {
            String prefix = "";
            Random gen = new Random();
            int n = gen.nextInt(100) + 1;
            if (ccType == s_DISCOVER) {
                  prefix = "6011";
            }
            else if (ccType == s_VISA) {
                  prefix = "4";
            }
            else if (ccType == s_AMEX) {
                  int remainder = n % 2;
                  if (remainder == 0)
                        prefix = "34";
                  else
                        prefix = "37";
            }
            else if (ccType == s_MASTERCARD) {
                  int remainder = n % 5;
                  if (remainder == 0)
                        prefix = "51";
                  else if (remainder == 1)
                        prefix = "52";
                  else if (remainder == 2)
                        prefix = "53";
                  else if (remainder == 3)
                        prefix = "54";
                  else
                        prefix = "55";
            }
            else if (ccType == s_DINERSCLUB) {
                  int remainder = n % 3;
                  if (remainder == 0)
                        prefix = "30";
                  else if (remainder == 1)
                        prefix = "36";
                  else
                        prefix = "38";
            }
            else {
                  prefix = null;
            }
            return prefix;
      }

      /**
       *
       * @param ccType
       * @return
       */

      public static int getCCLengthByType(int ccType) {
            int length = 0;
            if (ccType == s_VISA || ccType == s_MASTERCARD || ccType == s_DISCOVER) {
                  length = 16;
            }
            else if (ccType == s_AMEX) {
                  length = 15;
            }
            else if (ccType == s_DINERSCLUB) {
                  length = 14;
            }
            else {
                  length = -1;
            }
            return length;
      }

      public static int decodeString(String type) {
            if (type.equalsIgnoreCase("MC")) {
                  return s_MASTERCARD;
            }
            else if (type.equalsIgnoreCase("VISA")) {
                  return s_VISA;
            }
            else if (type.equalsIgnoreCase("AMEX")) {
                  return s_AMEX;
            }
            else if (type.equalsIgnoreCase("DINERS")) {
                  return s_DINERSCLUB;
            }
            else if (type.equalsIgnoreCase("DISCOVER")) {
                  return s_DISCOVER;
            }
            else {
                  return s_UNKNOWN;
            }
      }

      /**
       * true if cc format calculation is correct.
       * @param cardNum
       * @return
       */

      public static boolean validate(String cardNum) {
            boolean valid = false;
            String revCardNum = reverse(cardNum);
            int[] bits = extractCCBits(revCardNum);
            int luhn = calcLUHNSum(bits);

            if (luhn % 10 == 0) {
                  valid = true;
            }
            return valid;
      }

      public static int calcLUHNSum(int[] bits) {
            int sum = 0;
            for (int i=0; i< bits.length; i++) {
                  int temp = 0;
                  if (bits[i] >= 10)
                        temp = (bits[i] % 10) + 1;
                  else
                        temp = bits[i];
                  sum = sum + temp;
            }
            return sum;
      }

      /**
       *
       * @param cardNum
       * @return
       */
      public static int[] extractCCBits(String cardNum) {
        int[] bits = new int[cardNum.length()];
            for (int i=0;i<cardNum.length();i++) {
                  if (i % 2 != 0) {
                        int bit = Character.getNumericValue(cardNum.charAt(i));
                        //log("["+i+"]="+bit);
                      bits[i] = bit * 2;
                  }
                  else {
                        int bit = Character.getNumericValue(cardNum.charAt(i));
                        //log("["+i+"]="+bit);
                        bits[i] = bit;
                  }
            }
            return bits;
      }

      /**
       * Reverse the string
       * @param word
       * @return
       */
      public static String reverse(String word) {
            StringBuffer buf = new StringBuffer();
            for (int i = word.length()-1; i > -1; i--) {
                  buf.append(word.charAt(i));
            }
            return buf.toString();
      }

      /**
       *
       * @param msg
       */
      public static void log(String msg) {
            System.out.println(msg);
      }

      public static String getBitsInString(int[] bits) {
            StringBuffer buf = new StringBuffer();
            buf.append("[");
            for (int i : bits) {
                  buf.append("'");
                  buf.append(i);
                  buf.append("',");
            }
            buf.deleteCharAt(buf.lastIndexOf(","));
            buf.append("]");
            return buf.toString();
      }

      public static void main(String[] args) {
            String cardNum = "4502320008103480";
            log("CCNum = "+cardNum);
            String revCard = reverse(cardNum);
          log("RCCNum = " + revCard );
            int[] bits = extractCCBits(revCard);
            log(getBitsInString(bits));
            log("LUHN SUM = " +calcLUHNSum(bits));
            boolean v = validate(cardNum);
            log("v? => " + v);
            String cc = generateValidCCNumByType(CreditCardUtils.s_DISCOVER);
            String cc5 = generateValidCCNumByType(CreditCardUtils.s_MASTERCARD);
            String cc2 = generateValidCCNumByBinPrefix("409659");
            String cc3 = generateValidCCNumByBinPrefix("557069");
            String cc4 = generateValidCCNumByBinPrefix("45023200081");
            String cc6 = generateValidCCNumByType(CreditCardUtils.s_AMEX);
            log("Master:    " + cc3);
            log("Discover:  " + cc);
            log("Debit:     " + cc2);
            log("Visa :     " + cc4);
            log("Master:    " + cc5);
            log("Amex: " + cc6);
            
            log(""+ generateValidCCNumByBinPrefix("3455"));
            log ("234523452345" + validate("234523452345"));
            
            log ("3434343434343434" + validate("3434343434343434"));
            log ("343434343434343" + validate("343434343434343"));
            
      }
}

