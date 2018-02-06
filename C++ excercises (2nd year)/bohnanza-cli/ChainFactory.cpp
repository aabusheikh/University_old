#include "ChainFactory.h"

Chain_Base *ChainFactory::getChain(const Card* card) const
{
    switch (card->getType())
    {
        case 'Q':   return new Chain<Quartz>(card);
        case 'H':   return new Chain<Hematite>(card);
        case 'O':   return new Chain<Obsidian>(card);
        case 'M':   return new Chain<Malachite>(card);
        case 'T':   return new Chain<Turquoise>(card);
        case 'R':   return new Chain<Ruby>(card);
        case 'A':   return new Chain<Amethyst>(card);
        case 'E':   return new Chain<Emerald>(card);
        default:    return nullptr;
    }
}

Chain_Base *ChainFactory::getChain(istream& stream, CardFactory* factory) const
{
    char cardType;
    stream >> cardType;

    switch (cardType)
    {
        case 'Q':   return new Chain<Quartz>(stream, factory);
        case 'H':   return new Chain<Hematite>(stream, factory);
        case 'O':   return new Chain<Obsidian>(stream, factory);
        case 'M':   return new Chain<Malachite>(stream, factory);
        case 'T':   return new Chain<Turquoise>(stream, factory);
        case 'R':   return new Chain<Ruby>(stream, factory);
        case 'A':   return new Chain<Amethyst>(stream, factory);
        case 'E':   return new Chain<Emerald>(stream, factory);
        default:    return nullptr;
    }
}
