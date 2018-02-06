#ifndef CSI2372_TRADEAREA_H
#define CSI2372_TRADEAREA_H

#include <iostream>
#include <list>

#include "Card.h"
#include "CardFactory.h"

using std::istream;
using std::list;

class TradeArea
{
public:
    /**
     * Constructor which creates an empty trade area.
     */
    TradeArea();

    /**
     * Constructor which accepts an istream and reconstruct the TradeArea from file.
     * @param Input stream
     * @param Card factory
     */
    TradeArea(istream&, CardFactory*);

    /**
     * Serializes the TradeArea to file. Intended to be reconstructed with the istream constructor.
     * @param Output stream
     */
    void serializeTo(ostream&) const;

    /**
     * Adds the card to the trade area but it does not check if it is legal to place the card.
     * @param New card
     * @return This
     */
    TradeArea& operator+=(const Card*);

    /**
     * Returns true if the card can be legally added to the TradeArea, i.e., a card of
     * the same gemstone is already in the TradeArea.
     * @param A card
     * @return True if card can legally be added, false otherwise
     */
    bool legal(const Card*) const;

    /**
     * Returns the number of cards currently in the trade area.
     * @return Number of cards in the trade area
     */
    int numCards() const;

    /**
     * Insertion operator that prints all the cards of the trade area to an std::ostream.
     * @param Output stream
     * @param Trade area
     * @return
     */
    friend ostream& operator<<(ostream& os, const TradeArea& tradeArea);

    /**
     * Iterate over the cards in the trade area. This is needed for the beginning of the
     * player's turn, when he can choose to take or discard the cards that his opponent
     * left in the trade area.
     * @return Iterator of cards
     */
    list<const Card*>::const_iterator begin();
    list<const Card*>::const_iterator end();

    /**
     * Remove the card at the given position from the trade area.
     * @param Card iterator
     * @return New iterator (placed after the card)
     */
    list<const Card*>::const_iterator take(list<const Card*>::const_iterator);

private:
    list<const Card*> d_cards;
};


#endif //CSI2372_TRADEAREA_H
