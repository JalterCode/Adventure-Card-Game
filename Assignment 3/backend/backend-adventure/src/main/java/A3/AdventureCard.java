package A3;

public class AdventureCard extends Card implements Comparable<AdventureCard> {

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
    @Override
    public int compareTo(AdventureCard other) {
        // foe must come before weapon
        int subtypeComparison = this.subType.compareTo(other.subType);

        if (subtypeComparison != 0) {
            return subtypeComparison;  // "foe" < "weapon"
        }

        //if both have same subtype, then you are able to sort by value instead
        if (this.value != other.value) {
            return Integer.compare(this.value, other.value);
        }

        // this is to ensure that sword always comes before horse, can't search alphabetically here
        if (this.getName().equals("Sword") && other.getName().equals("Horse")) {
            return -1;  // Sword comes before Horse
        }
        if (this.getName().equals("Horse") && other.getName().equals("Sword")) {
            return 1;   // Horse comes after Sword
        }

        // For other cases, sort by name alphabetically
        return this.getName().compareTo(other.getName());
    }


}
