#ifndef CSI2372_ILLEGALTYPE_H
#define CSI2372_ILLEGALTYPE_H

#include <stdexcept>

using std::invalid_argument;

class IllegalType : public invalid_argument
{
public:
    IllegalType();
};

#endif //CSI2372_ILLEGALTYPE_H
