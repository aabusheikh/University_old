#include "Table.h"

using std::endl;

Table::Table(string player1Name, string player2Name)
    : d_player1(player1Name), d_player2(player2Name), d_deck(CardFactory::getFactory()->getDeck())
{
    for (int i = 0; i < 5; i++) /* each player draws 5 cards from the deck, alternating between players */
    {
        d_player1 += d_deck.draw();
        d_player2 += d_deck.draw();
    }
}

Table::Table(istream& stream, CardFactory* factory)
    : d_player1(stream, factory), d_player2(stream, factory), d_deck(stream, factory),
      d_discardPile(stream, factory), d_tradeArea(stream, factory)
{
}

bool Table::win(string& winner) const
{
    bool gameFinished = d_deck.empty();

    if (gameFinished)
        winner = (finalCoinCount(d_player1) > finalCoinCount(d_player2)) ? d_player1.getName() : d_player2.getName();

    return gameFinished;
}

int Table::finalCoinCount(const Player& player) const
{
    int numCoins = player.getNumCoins();

    /* We'll give each player coins for their unsold chains */
    for (int i = 0; i < player.getMaxNumChains(); i++)
    {
        if (player.chainExists(i))
            numCoins += player[i].sell();
    }

    return numCoins;
}

void Table::serializeTo(ostream& stream) const
{
    d_player1.serializeTo(stream);
    d_player2.serializeTo(stream);
    d_deck.serializeTo(stream);
    d_discardPile.serializeTo(stream);
    d_tradeArea.serializeTo(stream);
}

ostream &operator<<(ostream &stream, const Table &table)
{
    stream << table.d_player1 << endl;
    stream << table.d_player2 << endl;
    stream << table.d_discardPile << endl;
    stream << table.d_tradeArea;

    return stream;
}

