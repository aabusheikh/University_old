#ifndef CSI2372_CARDFACTORY_H
#define CSI2372_CARDFACTORY_H

#include <array>
#include <map>

#include "Deck.h"

using std::array;
using std::multimap;
using std::pair;

/*
 * The simplest way to keep track of instantiated cards was to create them as we go, and then add them
 * to a vector. Perhaps the better way (which matches more closely with the instructions) would be to always
 * instantiate all 104 cards from the CardFactory private constructor, and then serve cards from that pool
 * of card instances as they're needed (from getDeck() or getCard(istream&)).
 */
class CardFactory
{
public:
    /**
     * Returns a pointer to the only instance of CardFactory
     * @return Singleton instance to CardFactory
     */
    static CardFactory* getFactory();

    /**
     * Card destructor. Deletes all instantiated cards.
     */
    ~CardFactory();

    /**
     * Returns an instance of the next card found in the given stream. Intended for reconstructing cards
     * from file as a game save is being restored.
     * @param Input stream
     * @return Card instance
     */
    const Card* getCard(istream&);

    /**
     * Returns a deck with all 104 gemstone cards. Note that the 104 gemstone cards are always
     * the same but their order in the deck needs to be different every time.
     * @return Deck of 104 cards
     */
    Deck getDeck();

    /**
     * Delete both copy constructor and copy assignment operators to prevent this singleton
     * factory from being copied.
     */
    CardFactory(const CardFactory&) = delete;
    CardFactory& operator=(const CardFactory&) = delete;

private:
    static CardFactory* d_instance;
    array<const Card*, 104> d_cards;
    multimap<char, const Card*> d_cardsByType;

    CardFactory();

    template <class T>
    void createCards(int numCards, int& offset)
    {
        int end = offset + numCards;

        while (offset < end)
        {
            Card* card = new T();

            d_cards[offset++] = card;
            d_cardsByType.insert(multimap<char, Card*>::value_type(card->getType(), card));
        }
    }
};




#endif //CSI2372_CARDFACTORY_H
