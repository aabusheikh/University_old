#ifndef CSI2372_NOTENOUGHCOINS_H
#define CSI2372_NOTENOUGHCOINS_H

#include <stdexcept>

using std::logic_error;

class NotEnoughCoins : public logic_error
{
public:
    NotEnoughCoins();
};


#endif //CSI2372_NOTENOUGHCOINS_H
