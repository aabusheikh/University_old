#include <algorithm>
#include <random>
#include <chrono>

#include "CardFactory.h"

using std::begin;
using std::end;

// Allocating and initializing CardFactory's static data member. The pointer is being
// allocated - not the object itself.
CardFactory *CardFactory::d_instance = 0;

CardFactory* CardFactory::getFactory()
{
    if (!d_instance)
        d_instance = new CardFactory();

    return d_instance;
}

CardFactory::CardFactory()
{
    int offset = 0;

    createCards<Quartz>(20, offset);
    createCards<Hematite>(18, offset);
    createCards<Obsidian>(16, offset);
    createCards<Malachite>(14, offset);
    createCards<Turquoise>(12, offset);
    createCards<Ruby>(10, offset);
    createCards<Amethyst>(8, offset);
    createCards<Emerald>(6, offset);
}

CardFactory::~CardFactory()
{
    for (const Card* card : d_cards)
        delete card;
}

Deck CardFactory::getDeck()
{
    return Deck(d_cards);
}

const Card* CardFactory::getCard(istream& stream)
{
    char cardCode;
    stream >> cardCode;

    /* Grab the first card with the given one-letter code, and remove it from our pool so that it
     * doesn't get picked up again */
    auto it = d_cardsByType.find(cardCode);
    const Card* card = it->second;
    d_cardsByType.erase(it);

    return card;
}



