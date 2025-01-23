#include <stdint.h>
#include <pic32mx.h>
#include "mipslab.h" 
//define a compile-time constant for period
// 256 is for prescaling
// 10 ms is the time out period
#define TMR2PERIOD ((80000000 / 256) / 10)

int getsw( void ){

	int SW = (PORTD >> 8) & 0x000F;

	return SW;
}

int getbtns(void){

	int BTN = (PORTD >> 5) & 0x0007;

	return BTN;
}