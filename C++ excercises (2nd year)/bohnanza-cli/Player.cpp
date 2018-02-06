#include <iomanip>
#include "Player.h"
#include "NotEnoughCoins.h"

using std::endl;
using std::left;
using std::setw;

Player::Player(istream& stream, CardFactory* factory) : d_hand(stream, factory)
{
    getline(stream, d_name);
    d_name.erase(0, 1); /* first char will always be space from hand serialization */

    stream >> d_numCoins;
    stream >> d_maxNumChains;
    stream >> d_numChains;

    for (int i = 0; i < 3; i++)
        d_chains[i] = d_chainFactory.getChain(stream, factory);
}

/* Players initially have a maximum of 2 chains. I assumed they had 0 coins, but we'd need to double-check with the prof */
Player::Player(string& name)
    : d_name(name), d_numCoins(0), d_numChains(0), d_maxNumChains(2)
{

}

void Player::serializeTo(ostream& stream) const
{
    /* Using a line feed for the name to avoid issues with spaces in player names */
    d_hand.serializeTo(stream);
    stream << d_name << "\n" << d_numCoins << " " << d_maxNumChains << " " << d_numChains << " ";

    for (int i = 0; i < 3; i++)
    {
        if (d_chains[i] == nullptr)
            stream << '-';
        else
        {
            d_chains[i]->printCard(stream);
            d_chains[i]->serializeTo(stream);
        }
    }
}

Player::~Player()
{
    for (int i = 0; i < 3; i++)
    {
        if (d_chains[i] != nullptr)
            delete d_chains[i];
    }
}

void Player::buyThirdChain()
{
    if (d_numCoins < 3)
        throw NotEnoughCoins();

    d_numCoins -= 3;
    d_maxNumChains = 3;
}

ostream& operator<<(ostream& stream, const Player& player)
{
    stream << left << setw(20) << player.d_name << ' ' << player.d_numCoins << " coins" << endl;

    for (int i = 0; i < 3; i++)
    {
        if (player.d_chains[i] != nullptr)
            stream << *(player.d_chains[i]);
    }

    return stream;
}

Player& Player::operator+=(int coins)
{
    d_numCoins += coins;
    return *this;
}

int Player::hasChainFor(const Card* card) const
{
    for (int i = 0; i < d_maxNumChains; i++)
    {
        if (chainExists(i))
        {
            Chain_Base &chain = *(d_chains[i]);

            if (chain.legal(card))
                return i;
        }
    }

    return -1;
}

void Player::printHand(std::ostream& os, bool printWholeHand) const
{
    if (d_hand.empty())
        os << "EMPTY";
    else if (!printWholeHand)
        os << *d_hand.top();
    else
        os << d_hand;

    os << endl;
}

Player& Player::operator+=(const Card* card)
{
    d_hand += card;
    return *this;
}

const Card* Player::playTopCard()
{
    return d_hand.play();
}

Chain_Base& Player::operator[](int index) const
{
    return *(d_chains[index]);
}

bool Player::chainExists(int index) const
{
    return d_chains[index] != nullptr;
}

int Player::startChain(const Card* card)
{
    /* Find index of first free chain (if any) */
    int chainIndex;

    for (chainIndex = 0; chainIndex < d_maxNumChains; chainIndex++)
    {
        if (!chainExists(chainIndex))
            break;
    }

    if (chainIndex == d_maxNumChains)
        return -1;

    /* Create an instance of the specified card type */
    Chain_Base* chain = d_chainFactory.getChain(card);

    if (chain == nullptr)
        return -1;

    d_numChains++;
    d_chains[chainIndex] = chain;

    return chainIndex;
}

int Player::sellChain(int chainIndex)
{
    if (!chainExists(chainIndex))
        return -1;

    int chainValue = d_chains[chainIndex]->sell();

    d_numCoins += chainValue;
    delete d_chains[chainIndex];
    d_chains[chainIndex] = nullptr;
    d_numChains--;

    return chainValue;
}

int Player::getNumCards() const
{
    return d_hand.size();
}

const Card* Player::discard(int index)
{
    return d_hand[index];
}


