#include <iomanip>
#include "DiscardPile.h"

using std::endl;
using std::setw;
using std::left;

DiscardPile::DiscardPile()
{
}

DiscardPile::DiscardPile(istream& stream, CardFactory* factory)
{
    int numCards;

    stream >> numCards;

    for (int i = 0; i < numCards; i++)
        d_cards.push_back(factory->getCard(stream));
}

void DiscardPile::serializeTo(ostream& stream) const
{
    stream << d_cards.size() << " ";

    for (auto card : d_cards)
    {
        card->print(stream);
        stream << " ";
    }
}

ostream &operator<<(ostream &os, const DiscardPile &pile)
{
    os << left << setw(20) << "Discard pile" << " ";

    if (pile.d_cards.empty())
        os << "EMPTY";
    else
        pile.d_cards.front()->print(os);

    os << endl;

    return os;
}

DiscardPile& DiscardPile::operator+=(const Card* card)
{
	d_cards.insert(d_cards.begin(), card); /* the discarded card goes to the top of the pile */
	return *this;
}

const Card* DiscardPile::pickUp()
{
    const Card* card = nullptr;

    if (!d_cards.empty())
    {
        card = d_cards.front();
        d_cards.erase(d_cards.begin());
    }

	return card;
}

const Card* DiscardPile::top() const
{
	return d_cards.front();
}

bool DiscardPile::empty() const
{
    return d_cards.empty();
}
