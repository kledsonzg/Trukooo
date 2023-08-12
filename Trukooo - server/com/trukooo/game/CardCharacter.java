package com.trukooo.game;

public enum CardCharacter 
{
    CARD_CHARACTER_NONE,
    CARD_CHARACTER_AS,
    CARD_CHARACTER_KING,
    CARD_CHARACTER_JOKER,
    CARD_CHARACTER_QUEEN,
    CARD_CHARACTER_9,
    CARD_CHARACTER_8,
    CARD_CHARACTER_7,
    CARD_CHARACTER_6,
    CARD_CHARACTER_5,
    CARD_CHARACTER_4,
    CARD_CHARACTER_3,
    CARD_CHARACTER_2;

    
    public static CardCharacter getCardCharacterByIndex(int index)
    {
        switch(index)
        {
            case 1: return CardCharacter.CARD_CHARACTER_AS;
            case 2: return CardCharacter.CARD_CHARACTER_KING;
            case 3: return CardCharacter.CARD_CHARACTER_JOKER;
            case 4: return CardCharacter.CARD_CHARACTER_QUEEN;
            case 5: return CardCharacter.CARD_CHARACTER_9;
            case 6: return CardCharacter.CARD_CHARACTER_8;
            case 7: return CardCharacter.CARD_CHARACTER_7;
            case 8: return CardCharacter.CARD_CHARACTER_6;
            case 9: return CardCharacter.CARD_CHARACTER_5;
            case 10: return CardCharacter.CARD_CHARACTER_4;
            case 11: return CardCharacter.CARD_CHARACTER_3;
            case 12: return CardCharacter.CARD_CHARACTER_2;

            default: return CardCharacter.CARD_CHARACTER_NONE;
        }
    }
}
