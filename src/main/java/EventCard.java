public class EventCard extends Card {

    public EventCard(String cardType, String name, int amount) {
        super(cardType, name, amount);
    }

    public void handleEvent(EventCard card, Player player, Game game){
        int currentShields = player.getShields();
        if(card.getName().equals("Plague")){

            if(currentShields <= 2){
                player.setShields(0);
            } else{
                player.setShields(currentShields - 2);
            }
        }
        if(card.getName().equals("Queen's Favor")){
            game.drawAdventureCard(player);
            game.drawAdventureCard(player);
        }
        if(card.getName().equals("Prosperity")){
            for(Player player1: game.players){
                System.out.println(player1.getID() + " draws 2");
                game.drawAdventureCard(player1);
                game.drawAdventureCard(player1);
                if(player1.getHand().size() > 12){
                    game.trimHand(player1);
                }
            }
        }
    }
}
