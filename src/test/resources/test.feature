Feature: Assignment 2 Tests


  Scenario: A1_scenario
    Given a new game is started
    And a randomized hand is dealt
    And the event deck is setup to draw "Q4" in that order
    And adventure deck is set up to ensure players draw the correct cards
    And the players are dealt their correct initial hands
    When "P1" draws a quest of 4 stages and "declines"
    And "P2" chooses to "accept"
    And "P2" builds "stage 1" by using the cards "F5, Horse"
    And "P2" builds "stage 2" by using the cards "F15, Sword"
    And "P2" builds "stage 3" by using the cards "F15, Dagger, Battle Axe"
    And "P2" builds "stage 4" by using the cards "F40, Battle Axe"

    #STAGE 1
    And "P1", "P3", "P4" participate and "everyone" discards
    And "P1" builds "attack 1" by using the cards "Dagger, Sword"
    And "P3" builds "attack 1" by using the cards "Dagger, Sword"
    And "P4" builds "attack 1" by using the cards "Dagger, Horse"
    #STAGE 2
    And "P1", "P3", "P4" participate
    And "P1" builds "attack 2" by using the cards "Sword, Horse"
    And "P3" builds "attack 2" by using the cards "Sword, Battle Axe"
    And "P4" builds "attack 2" by using the cards "Horse, Battle Axe"

    #STAGE 3
    And "P3", "P4" participate
    And "P3" builds "attack 3" by using the cards "Sword, Horse, Lance"
    And "P4" builds "attack 3" by using the cards "Sword, Battle Axe, Lance"

    #STAGE 4
    And "P3", "P4" participate
    And "P3" builds "attack 4" by using the cards "Horse, Lance, Battle Axe"
    And "P4" builds "attack 4" by using the cards "Dagger, Sword, Lance, Excalibur"
    And "P2" trims their hand down to 12 cards from 16
    And Quest logic is resolved

    #As P4 has 4 shields, they won the quest
    Then "P4" should have 4 shields
    And "P4" should have the hand: "[F15, F15, F40, Lance]"
    And "P1" should have 0 shields
    And "P1" should have the hand: "[F5, F10, F15, F15, F30, Horse, Battle Axe, Battle Axe, Lance]"
    And "P2" should have 12 cards in hand
    And "P3" should have 0 shields
    And "P3" should have the hand: "[F5, F5, F15, F30, Sword]"





  Scenario: 2winner_game_2winner_quest
    Given a new game is started
    And the event deck is setup to draw "Q4, Q3" in that order
    And the players are dealt their correct initial hands
    And adventure deck is set up to ensure players draw the correct cards
    When "P1" draws a quest of 4 stages and "accepts"

    #Stage 1 single foe
    And "P1" builds "stage 1" by using the cards "F15"

    And "P1" builds "stage 2" by using the cards "F15, Dagger"
    And "P1" builds "stage 3" by using the cards "F5, Lance"
    And "P1" builds "stage 4" by using the cards "F5, Sword, Battle Axe"
    And "P2", "P3", "P4" participate and "everyone" discards

    #STAGE 1
    And "P2" builds "attack" by using the cards "Dagger, Horse"

    #P3 attack fails
    And "P3" builds "attack" by using the cards "Dagger"

    And "P4" builds "attack" by using the cards "Dagger, Horse"

    #STAGE 2
    And "P2", "P4" participate
    And "P2" builds "attack" by using the cards "Sword, Horse"
    And "P4" builds "attack" by using the cards "Sword, Horse"


    #STAGE 3
    And "P2", "P4" participate
    And "P2" builds "attack" by using the cards "Battle Axe, Lance"
    And "P4" builds "attack" by using the cards "Dagger, Lance"


    #STAGE 4
    And "P2", "P4" participate
    And "P2" builds "attack" by using the cards "Excalibur"
    And "P4" builds "attack" by using the cards "Excalibur"

    And "P1" draws 11 cards from stage being resolved
    And "P1" trims their hand down to 12 cards from 16
    And Quest logic is resolved

    And "P2" draws a quest of 3 stages and "declines"
    And "P3" chooses to "accept"

    And "P3" builds "stage 1" by using the cards "F5"
    And "P3" builds "stage 2" by using the cards "F15"
    And "P3" builds "stage 3" by using the cards "F5, Sword, Horse"

    #STAGE 1
    And "P1" chooses to "decline"
    And "P2", "P4" participate
    And "P2" builds "attack" by using the cards "Sword"
    And "P4" builds "attack" by using the cards "Battle Axe"

    #Stage 2
    And "P2", "P4" participate

    And "P2" builds "attack" by using the cards "Battle Axe"
    And "P4" builds "attack" by using the cards "Battle Axe"

    #Stage 3
    And "P2", "P4" participate
    And "P2" builds "attack" by using the cards "Excalibur"
    And "P4" builds "attack" by using the cards "Excalibur"

    And "P3" trims their hand down to 12 cards from 14
    And Quest logic is resolved
    Then "P2, P4" should have 7 shields
    And "P2, P4" determined as winner






    Scenario: 1winner_game_with_events
      Given a new game is started
      And the event deck is setup to draw "Q4, Plague, Prosperity, Queen's Favor, Q3" in that order
      And the players are dealt their correct initial hands
      And adventure deck is set up to ensure players draw the correct cards
      When "P1" draws a quest of 4 stages and "accepts"
      And "P1" builds "stage 1" by using the cards "F5"
      And "P1" builds "stage 2" by using the cards "F15"
      And "P1" builds "stage 3" by using the cards "F15, Dagger"
      And "P1" builds "stage 4" by using the cards "F5, Sword, Horse"

      #STAGE 1
      And "P2", "P3", "P4" participate and "everyone" discards
      And "P2" builds "attack" by using the cards "Dagger"
      And "P3" builds "attack" by using the cards "Dagger"
      And "P4" builds "attack" by using the cards "Dagger"

      #STAGE 2
      And "P2", "P3", "P4" participate
      And "P2" builds "attack" by using the cards "Sword, Horse"
      And "P3" builds "attack" by using the cards "Sword, Horse"
      And "P4" builds "attack" by using the cards "Dagger, Sword"

      #STAGE 3
      And "P2", "P3", "P4" participate
      And "P2" builds "attack" by using the cards "Horse, Battle Axe"
      And "P3" builds "attack" by using the cards "Sword, Horse"
      And "P4" builds "attack" by using the cards "Horse, Battle Axe"

      #STAGE 4
      And "P2", "P3", "P4" participate
      And "P2" builds "attack" by using the cards "Excalibur"
      And "P3" builds "attack" by using the cards "Excalibur"
      And "P4" builds "attack" by using the cards "Excalibur"

      And "P1" draws 11 cards from stage being resolved
      And "P1" trims their hand down to 12 cards from 16

      And Quest logic is resolved
      And "P2" draws "Plague"
      And "P3" draws "Prosperity"
      And "P4" draws "Queen's Favor"


      And "P1" draws a quest of 3 stages and "accepts"

      And "P1" builds "stage 1" by using the cards "F5, Sword"
      And "P1" builds "stage 2" by using the cards "F15, Horse"
      And "P1" builds "stage 3" by using the cards "F20, Battle Axe"

      #STAGE 1
      And "P2", "P3", "P4" participate and "P4" discards
      And "P2" builds "attack" by using the cards "Battle Axe"
      And "P3" builds "attack" by using the cards "Excalibur"
      And "P4" builds "attack" by using the cards "Horse"

      #STAGE 2
      And "P2", "P3" participate
      And "P2" builds "attack" by using the cards "Excalibur"
      And "P3" builds "attack" by using the cards "Excalibur"

      #STAGE 3
      And "P2", "P3" participate
      And "P2" builds "attack" by using the cards "Battle Axe, Excalibur"
      And "P3" builds "attack" by using the cards "Sword, Excalibur"

      And "P1" trims their hand down to 12 cards from 15
      And Quest logic is resolved


      Then "P3" determined as winner
      And "P3" should have 7 shields
      And "P2" should have 5 shields
      And "P4" should have 4 shields

      ##CAN REFACTOR EVENT DECK DRAW TO USE IF STATEMENT WITH STRING








  Scenario: 0_winner_quest
    Given a new game is started
    And the event deck is setup to draw "Q2" in that order
    And the players are dealt their correct initial hands
    And adventure deck is set up to ensure players draw the correct cards
    When "P1" draws a quest of 2 stages and "accepts"
    And "P1" builds "stage 1" by using the cards "F15"
    And "P1" builds "stage 2" by using the cards "F15, Sword"

    #STAGE 1, ALL ATTACKS WILL FAIL
    And "P2", "P3", "P4" participate and "everyone" discards
    And "P2" builds "attack" by using the cards "Dagger"
    And "P3" builds "attack" by using the cards "Dagger"
    And "P4" builds "attack" by using the cards "Dagger"


    And "P1" draws 9 cards from stage being resolved
    And "P1" trims their hand down to 12 cards from 14
    And Quest logic is resolved
    Then the quest should end with no winner
    And "P2" should have 0 shields
    And "P3" should have 0 shields
    And "P4" should have 0 shields
    And "P2" should have 11 cards in hand
    And "P3" should have 11 cards in hand
    And "P4" should have 11 cards in hand

    #Will discard 2 F5s
    And P1 should no longer have any F5
    And P1 should have new cards
