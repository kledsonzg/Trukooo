package com.trukooo.game;

public enum CardSymbol 
{
    CARD_SYMBOL_CLUB,
    CARD_SYMBOL_HEART,
    CARD_SYMBOL_SPADE,
    CARD_SYMBOL_DIAMOND,
    CARD_SYMBOL_NONE;

    public static CardSymbol getCardSymbolByIndex(int index)
    {
        switch(index)
        {
            case 0: return CardSymbol.CARD_SYMBOL_CLUB;
            case 1: return CardSymbol.CARD_SYMBOL_HEART;
            case 2: return CardSymbol.CARD_SYMBOL_SPADE;
            case 3: return CardSymbol.CARD_SYMBOL_DIAMOND;

            default: return CardSymbol.CARD_SYMBOL_NONE;
        }
    }
}
