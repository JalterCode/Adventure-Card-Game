public class AdventureCard extends Card {

    private int value;
    private String subType;


    public AdventureCard(String cardType, String name, int value, int amount,String subType) {
        super(cardType, name, amount);
        this.value = value;
        this.subType = subType;
    }

    public int getValue(){
        return value;
    }

    public String getSubType(){
        return subType;
    }

}
