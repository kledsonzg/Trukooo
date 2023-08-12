package com.trukooo.game;

public class Card 
{
    CardSymbol symbol = CardSymbol.CARD_SYMBOL_NONE;
    CardCharacter character = CardCharacter.CARD_CHARACTER_NONE;

    public Card(CardSymbol cardSymbol, CardCharacter cardCharacter)
    {
        symbol = cardSymbol;
        character = cardCharacter;
    }
}
