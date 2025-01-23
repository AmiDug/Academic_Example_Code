/* mipslabwork.c

   This file written 2015 by F Lundevall

   This file should be changed by YOU! So add something here:

   This file modified 2015-12-24 by Ture Teknolog 

   Latest update 2015-08-28 by F Lundevall

   For copyright and licensing, see file COPYING */

#include <stdint.h>   /* Declarations of uint_32 and the like */
#include <pic32mx.h>  /* Declarations of system-specific addresses etc */
#include "mipslab.h"  /* Declatations for these labs */
#define TMR2PERIOD ((80000000 / 256) / 10)

int mytime = 0x5957;
int timeoutcount = 0;

char textstring[] = "text, more text, and even more text!";

/* Interrupt Service Routine */
void user_isr( void )
{
  return;
}

/* Lab-specific initialization goes here */
void labinit( void )
{
  // defining the int pointer, trise, volatile because you 
  //don't want the c compiler to optimise
  volatile int * E = (volatile int *) 0xbf886100;
  // 
  volatile int * portE = (volatile int *) 0xbf886110;
  * portE = 0x0; // for LED outputs

  // for LEDs ticking
  * portE =  * portE + 0x1;
  // only for the last 8 bits
  * E = * E & 0xFF00;
  // initialising port D as input
  // using the pic32mx system
  TRISD = TRISD & 0x0FE0;

  PR2 = TMR2PERIOD;
  T2CONSET = 0x70; // setting the prescale
  TMR2 = 0; // reset timer to 0
  T2CONSET = 0x8000; // turn timer on, set bit 15 to 1

  return;
}

/* This function is called repetitively from the main program */
void labwork( void )
{
  int BTN;
  int SW;

  BTN = getbtns();
  SW = getsw();
    
  //for button 2
    if(BTN == 1 || BTN == 3 || BTN == 5 || BTN == 7){
      mytime = mytime & 0xFF0F;
      mytime = (SW << 4) | mytime;
    }
    // for button 3
    if(BTN == 2 || BTN == 3 || BTN == 6 || BTN == 7){
      mytime = mytime & 0xF0FF;
      mytime = (SW << 8) | mytime;
    }
    // for button 4
    if(BTN == 4 || BTN == 5 || BTN == 6 || BTN == 7){
      mytime = mytime & 0x0FFF;
      mytime = (SW << 12) | mytime;
    }

  // 
  if(IFS(0) & 0x100){

    // incrementing counter
    timeoutcount++;
    // reseting flag
    IFSCLR(0) = 0x100;
  }

  // only execute on every tenth time-out cycle
  if(timeoutcount == 10){

    time2string( textstring, mytime );
    display_string( 3, textstring );
    display_update();
    tick( &mytime );
    display_image(96, icon); 

    timeoutcount = 0;
    
  }

}
