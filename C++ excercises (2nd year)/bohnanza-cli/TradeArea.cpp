/*
 * Copyright (C) 2018  Ahmad A. A. (https://github.com/bbpgrs/), 
 *                     Nicholas Gagnon (https://github.com/ngagnon)
 */

#include <iomanip>
#include "TradeArea.h"

using std::endl;
using std::setw;
using std::left;

TradeArea::TradeArea()
{
}

TradeArea::TradeArea(istream& stream, CardFactory* factory)
{
    int numCards;

    stream >> numCards;

    for (int i = 0; i < numCards; i++)
        d_cards.push_back(factory->getCard(stream));
}

void TradeArea::serializeTo(ostream& stream) const
{
    stream << d_cards.size() << " "; /* pack the number of cards */

    /* followed by the code of each card */
    for (auto card  : d_cards)
    {
        card->print(stream);
        stream << " ";
    }
}

ostream& operator<<(ostream &os, const TradeArea &tradeArea)
{
    os << left << setw(20) << "Trade area" << ' ';

    if (tradeArea.d_cards.empty())
        os << "EMPTY";

    for (auto card : tradeArea.d_cards)
    {
        card->print(os);
        os << ' ';
    }

    os << endl;

    return os;
}

TradeArea& TradeArea::operator+=(const Card* card)
{
	d_cards.push_back(card);
	return *this;
}

bool TradeArea::legal(const Card* card) const
{
	for (auto currentCard : d_cards)
	{
		if (currentCard->getName() == card->getName())
            return true;
	}

	return false;
}

list<const Card*>::const_iterator TradeArea::begin()
{
    return d_cards.cbegin();
}

list<const Card*>::const_iterator TradeArea::end()
{
    return d_cards.cend();
}

list<const Card*>::const_iterator TradeArea::take(list<const Card*>::const_iterator iter)
{
    return d_cards.erase(iter);
}

int TradeArea::numCards() const
{
	return d_cards.size();
}
