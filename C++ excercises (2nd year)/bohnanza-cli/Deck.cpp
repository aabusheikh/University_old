#include <random>
#include <chrono>

#include "Deck.h"
#include "CardFactory.h"

Deck::Deck(istream& stream, CardFactory* factory)
{
    unsigned long numCards;

    stream >> numCards;
    reserve(numCards); /* we know how large our vector needs to be, so we might as well allocate it to size */

    for (int i = 0; i < numCards; i++)
        push_back(factory->getCard(stream));
}

Deck::Deck(array<const Card*, 104>& cards)
{
    reserve(104); /* we know how large our vector needs to be, so we might as well allocated it to size */
    insert(end(), cards.begin(), cards.end());

    auto seed = std::chrono::system_clock::now().time_since_epoch().count();
    auto engine = std::default_random_engine(seed);
    std::shuffle(begin(), end(), engine);
}

void Deck::serializeTo(ostream& stream) const
{
    stream << size() << " "; /* pack the number of cards */

    /* followed by the code of each card */
    for (auto card  : *this)
    {
        card->print(stream);
        stream << " ";
    }
}

const Card* Deck::draw()
{
    if (empty())
        return nullptr;

	const Card* topCard = front();
	erase(begin());

	return topCard;
}

bool Deck::empty() const
{
    return vector<const Card*>::empty();
}


