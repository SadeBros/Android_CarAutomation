package com.automation.CarAutomation.Model;

import java.util.Locale;

public class Alarm {

    private long MASK_ALARM_ID           = 0xFF000000;      //  8 bit;

    private long MASK_RELAY_4            = 0x00C00000;      //  2 bit;
    private long MASK_RELAY_3            = 0x00300000;      //  2 bit;
    private long MASK_RELAY_2            = 0x000C0000;      //  2 bit;
    private long MASK_RELAY_1            = 0x00030000;      //  2 bit;

    private long MASK_REPEAT             = 0x0000C000;      //  2 bit;
    private long MASK_DAY_OF_WEEK        = 0x00003800;      //  3 bit;
    private long MASK_HOUR               = 0x000007C0;      //  5 bit;
    private long MASK_MINUTE             = 0x0000003F;      //  6 bit;

    private long MASK_DAY_TIME           = 0x00003FFF;


            /*              DIGIT_VALUE             */
    private long DIGIT_VALUE_ID          = 0x01000000;

    private long DIGIT_VALUE_RELAY_4     = 0x00400000;
    private long DIGIT_VALUE_RELAY_3     = 0x00100000;
    private long DIGIT_VALUE_RELAY_2     = 0x00040000;
    private long DIGIT_VALUE_RELAY_1     = 0x00010000;

    private long DIGIT_VALUE_REPEAT      = 0x00004000;
    private long DIGIT_VALUE_DAY_OF_WEEK = 0x00000800;
    private long DIGIT_VALUE_HOUR        = 0x00000040;
    private long DIGIT_VALUE_MINUTE      = 0x00000001;


    private boolean enable = false;

    private long alarmDescription;

    public Alarm(){}


    public Alarm(long alarmDescription) {
        this.alarmDescription = alarmDescription;
    }


    public String getAlarmSetCommand() {
        return "as " + String.valueOf(alarmDescription) + " ;";
    }

    public String getAlarmDisarmCommand(){
        return "ad " + id() + " ;";
    }

    public String getDigitalClockFormat() {
        return String.format(Locale.getDefault(), "%02d:%02d", hour(), minute());
    }

    public long id()                {  return ( alarmDescription & MASK_ALARM_ID  ) / DIGIT_VALUE_ID ; }
    public boolean isEnable()       {  return enable; }

    public long relay4()            {  return ( alarmDescription & MASK_RELAY_4   ) / DIGIT_VALUE_RELAY_4 ; }
    public long relay3()            {  return ( alarmDescription & MASK_RELAY_3   ) / DIGIT_VALUE_RELAY_3 ; }
    public long relay2()            {  return ( alarmDescription & MASK_RELAY_2   ) / DIGIT_VALUE_RELAY_2 ; }
    public long relay1()            {  return ( alarmDescription & MASK_RELAY_1   ) / DIGIT_VALUE_RELAY_1 ; }

    public long repeat()            {  return ( alarmDescription & MASK_REPEAT    ) / DIGIT_VALUE_REPEAT ; }
    public long minute()            {  return ( alarmDescription & MASK_MINUTE    ) / DIGIT_VALUE_MINUTE ; }
    public long hour()              {  return ( alarmDescription & MASK_HOUR      ) / DIGIT_VALUE_HOUR ; }
    public long dayWeek()           {  return ( alarmDescription & MASK_DAY_OF_WEEK)/ DIGIT_VALUE_DAY_OF_WEEK ; }
    public long dayTime()           {  return ( alarmDescription & MASK_DAY_TIME);}


    public void setId( long id ) {
        alarmDescription &= onesComplement( MASK_ALARM_ID );       //  Previous id is cleared.
        alarmDescription += id * DIGIT_VALUE_ID;                   //  The new one is set.
    }
    public void setEnable( boolean enable ) {
        this.enable = enable;
    }




    public void setRelay4(long relay4) {
        alarmDescription &= onesComplement( MASK_RELAY_4 );
        alarmDescription += relay4 * DIGIT_VALUE_RELAY_4;
    }
    public void setRelay3(long relay3) {
        alarmDescription &= onesComplement( MASK_RELAY_3 );
        alarmDescription += relay3 * DIGIT_VALUE_RELAY_3;
    }
    public void setRelay2(long relay2) {
        alarmDescription &= onesComplement( MASK_RELAY_2 );
        alarmDescription += relay2 * DIGIT_VALUE_RELAY_2;
    }
    public void setRelay1(long relay1) {
        alarmDescription &= onesComplement( MASK_RELAY_1 );
        alarmDescription += relay1 * DIGIT_VALUE_RELAY_1;
    }



    public void setRepeat(long repeat){
        alarmDescription &= onesComplement( MASK_REPEAT );
        alarmDescription += repeat * DIGIT_VALUE_REPEAT;
    }
    public void setMinute(long minute){
        alarmDescription &= onesComplement( MASK_MINUTE );
        alarmDescription += minute * DIGIT_VALUE_MINUTE;
    }
    public void setHour(long hour){
        alarmDescription &= onesComplement( MASK_HOUR );
        alarmDescription += hour * DIGIT_VALUE_HOUR;
    }
    public void setDayOfWeek(long dayWeek){
        alarmDescription &= onesComplement( MASK_DAY_OF_WEEK );
        alarmDescription += dayWeek * DIGIT_VALUE_DAY_OF_WEEK;
    }

    public String getDescription()
    {
        return ("id: " +          String.valueOf( id()                ) + " " +
                "isEnable: " +    String.valueOf( isEnable()          ) + " " +
                "relay4: " +      String.valueOf( relay4()            ) + " " +
                "relay3: " +      String.valueOf( relay3()            ) + " " +
                "relay2: " +      String.valueOf( relay2()            ) + " " +
                "relay1: " +      String.valueOf( relay1()            ) + " " +
                "repeat: " +      String.valueOf( repeat()            ) + " " +
                "minute: " +      String.valueOf( minute()            ) + " " +
                "hour: " +        String.valueOf( hour()              ) + " " +
                "dayWeek: " +     String.valueOf( dayWeek()           ));
    }

    public String getRepeatText(){
        if( repeat() == 0 ) return "Bir kez";
        if( repeat() == 1 ) return "Günlük";
        if( repeat() == 2 ) return "Haftalık";

        return "-1";
    }

    public String getAlarmDay() {

        if( dayWeek() == 1 )    return "Pazartesi";
        if( dayWeek() == 2 )    return "Salı";
        if( dayWeek() == 3 )    return "Çarşamba";
        if( dayWeek() == 4 )    return "Perşembe";
        if( dayWeek() == 5 )    return "Cuma";
        if( dayWeek() == 6 )    return "Cumartesi";
        if( dayWeek() == 7 )    return "Pazar";

        return "";
    }

    public String getDateTimeInformation(){
        return  getDigitalClockFormat() + "\n" +
                getRepeatText() + " " + getAlarmDay() ;
    }

    public String getRelayInformation(){
        SharedPreferencesContainer sharedPreferencesContainer = SharedPreferencesContainer.getInstance();
        return  sharedPreferencesContainer.get_name_of_relay("1") + " " + getInformationOfRelay("1") + "\n" +
                sharedPreferencesContainer.get_name_of_relay("2") + " " + getInformationOfRelay("2") + "\n" +
                sharedPreferencesContainer.get_name_of_relay("3") + " " + getInformationOfRelay("3") + "\n" +
                sharedPreferencesContainer.get_name_of_relay("4") + " " + getInformationOfRelay("4") ;

    }

    private String getInformationOfRelay(String relayNumber){
        if( relayNumber.equals("1"))    return getRelayText(relay1());
        if( relayNumber.equals("2"))    return getRelayText(relay2());
        if( relayNumber.equals("3"))    return getRelayText(relay3());
        if( relayNumber.equals("4"))    return getRelayText(relay4());

        return "";
    }

    private String getRelayText(long relayStatus){
        if( relayStatus == 0)   return "Kapa";
        if( relayStatus == 1)   return "Aç";
        if( relayStatus == 2)   return "Değiş";
        if( relayStatus == 3)   return "-";

        return "";
    }

    private long onesComplement(long number){ return (0xFFFFFFFF - number) ;}

}
