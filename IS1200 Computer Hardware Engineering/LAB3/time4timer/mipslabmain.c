/*
 * I2C Example project for the mcb32 toolchain
 * Demonstrates the temperature sensor and display of the Basic IO Shield
 * Make sure your Uno32-board has the correct jumper settings, as can be seen
 * in the rightmost part of this picture:
 * https://reference.digilentinc.com/_media/chipkit_uno32:jp6_jp8.png?w=300&tok=dcceb2
 */

#include <pic32mx.h>
#include <stdint.h>
#include <stdbool.h>
#include <stdio.h>
#include "mipslab.h"

#define DISPLAY_VDD_PORT PORTF
#define DISPLAY_VDD_MASK 0x40
#define DISPLAY_VBATT_PORT PORTF
#define DISPLAY_VBATT_MASK 0x20
#define DISPLAY_COMMAND_DATA_PORT PORTF
#define DISPLAY_COMMAND_DATA_MASK 0x10
#define DISPLAY_RESET_PORT PORTG
#define DISPLAY_RESET_MASK 0x200
#define TMR2PERIOD ((80000000 / 256) / 10)

int timeoutcount = 0;

/* Address of the temperature sensor on the I2C bus */
#define TEMP_SENSOR_ADDR 0x48

/* Temperature sensor internal registers */
typedef enum TempSensorReg TempSensorReg;
enum TempSensorReg {
	TEMP_SENSOR_REG_TEMP,
	TEMP_SENSOR_REG_CONF,
	TEMP_SENSOR_REG_HYST,
	TEMP_SENSOR_REG_LIMIT,
};


char textbuffer[4][16];

static const uint8_t const font[] = {
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 2, 5, 2, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 94, 0, 0, 0, 0,
	0, 0, 4, 3, 4, 3, 0, 0,
	0, 36, 126, 36, 36, 126, 36, 0,
	0, 36, 74, 255, 82, 36, 0, 0,
	0, 70, 38, 16, 8, 100, 98, 0,
	0, 52, 74, 74, 52, 32, 80, 0,
	0, 0, 0, 4, 3, 0, 0, 0,
	0, 0, 0, 126, 129, 0, 0, 0,
	0, 0, 0, 129, 126, 0, 0, 0,
	0, 42, 28, 62, 28, 42, 0, 0,
	0, 8, 8, 62, 8, 8, 0, 0,
	0, 0, 0, 128, 96, 0, 0, 0,
	0, 8, 8, 8, 8, 8, 0, 0,
	0, 0, 0, 0, 96, 0, 0, 0,
	0, 64, 32, 16, 8, 4, 2, 0,
	0, 62, 65, 73, 65, 62, 0, 0,
	0, 0, 66, 127, 64, 0, 0, 0,
	0, 0, 98, 81, 73, 70, 0, 0,
	0, 0, 34, 73, 73, 54, 0, 0,
	0, 0, 14, 8, 127, 8, 0, 0,
	0, 0, 35, 69, 69, 57, 0, 0,
	0, 0, 62, 73, 73, 50, 0, 0,
	0, 0, 1, 97, 25, 7, 0, 0,
	0, 0, 54, 73, 73, 54, 0, 0,
	0, 0, 6, 9, 9, 126, 0, 0,
	0, 0, 0, 102, 0, 0, 0, 0,
	0, 0, 128, 102, 0, 0, 0, 0,
	0, 0, 8, 20, 34, 65, 0, 0,
	0, 0, 20, 20, 20, 20, 0, 0,
	0, 0, 65, 34, 20, 8, 0, 0,
	0, 2, 1, 81, 9, 6, 0, 0,
	0, 28, 34, 89, 89, 82, 12, 0,
	0, 0, 126, 9, 9, 126, 0, 0,
	0, 0, 127, 73, 73, 54, 0, 0,
	0, 0, 62, 65, 65, 34, 0, 0,
	0, 0, 127, 65, 65, 62, 0, 0,
	0, 0, 127, 73, 73, 65, 0, 0,
	0, 0, 127, 9, 9, 1, 0, 0,
	0, 0, 62, 65, 81, 50, 0, 0,
	0, 0, 127, 8, 8, 127, 0, 0,
	0, 0, 65, 127, 65, 0, 0, 0,
	0, 0, 32, 64, 64, 63, 0, 0,
	0, 0, 127, 8, 20, 99, 0, 0,
	0, 0, 127, 64, 64, 64, 0, 0,
	0, 127, 2, 4, 2, 127, 0, 0,
	0, 127, 6, 8, 48, 127, 0, 0,
	0, 0, 62, 65, 65, 62, 0, 0,
	0, 0, 127, 9, 9, 6, 0, 0,
	0, 0, 62, 65, 97, 126, 64, 0,
	0, 0, 127, 9, 9, 118, 0, 0,
	0, 0, 38, 73, 73, 50, 0, 0,
	0, 1, 1, 127, 1, 1, 0, 0,
	0, 0, 63, 64, 64, 63, 0, 0,
	0, 31, 32, 64, 32, 31, 0, 0,
	0, 63, 64, 48, 64, 63, 0, 0,
	0, 0, 119, 8, 8, 119, 0, 0,
	0, 3, 4, 120, 4, 3, 0, 0,
	0, 0, 113, 73, 73, 71, 0, 0,
	0, 0, 127, 65, 65, 0, 0, 0,
	0, 2, 4, 8, 16, 32, 64, 0,
	0, 0, 0, 65, 65, 127, 0, 0,
	0, 4, 2, 1, 2, 4, 0, 0,
	0, 64, 64, 64, 64, 64, 64, 0,
	0, 0, 1, 2, 4, 0, 0, 0,
	0, 0, 48, 72, 40, 120, 0, 0,
	0, 0, 127, 72, 72, 48, 0, 0,
	0, 0, 48, 72, 72, 0, 0, 0,
	0, 0, 48, 72, 72, 127, 0, 0,
	0, 0, 48, 88, 88, 16, 0, 0,
	0, 0, 126, 9, 1, 2, 0, 0,
	0, 0, 80, 152, 152, 112, 0, 0,
	0, 0, 127, 8, 8, 112, 0, 0,
	0, 0, 0, 122, 0, 0, 0, 0,
	0, 0, 64, 128, 128, 122, 0, 0,
	0, 0, 127, 16, 40, 72, 0, 0,
	0, 0, 0, 127, 0, 0, 0, 0,
	0, 120, 8, 16, 8, 112, 0, 0,
	0, 0, 120, 8, 8, 112, 0, 0,
	0, 0, 48, 72, 72, 48, 0, 0,
	0, 0, 248, 40, 40, 16, 0, 0,
	0, 0, 16, 40, 40, 248, 0, 0,
	0, 0, 112, 8, 8, 16, 0, 0,
	0, 0, 72, 84, 84, 36, 0, 0,
	0, 0, 8, 60, 72, 32, 0, 0,
	0, 0, 56, 64, 32, 120, 0, 0,
	0, 0, 56, 64, 56, 0, 0, 0,
	0, 56, 64, 32, 64, 56, 0, 0,
	0, 0, 72, 48, 48, 72, 0, 0,
	0, 0, 24, 160, 160, 120, 0, 0,
	0, 0, 100, 84, 84, 76, 0, 0,
	0, 0, 8, 28, 34, 65, 0, 0,
	0, 0, 0, 126, 0, 0, 0, 0,
	0, 0, 65, 34, 28, 8, 0, 0,
	0, 0, 4, 2, 4, 2, 0, 0,
	0, 120, 68, 66, 68, 120, 0, 0,
};

void delay(int cyc) {
	int i;
	for(i = cyc; i > 0; i--);
}

uint8_t spi_send_recv(uint8_t data) {
	while(!(SPI2STAT & 0x08));
	SPI2BUF = data;
	while(!(SPI2STAT & 0x01));
	return SPI2BUF;
}

void display_init() {
	DISPLAY_COMMAND_DATA_PORT &= ~DISPLAY_COMMAND_DATA_MASK;
	delay(10);
	DISPLAY_VDD_PORT &= ~DISPLAY_VDD_MASK;
	delay(1000000);

	spi_send_recv(0xAE);
	DISPLAY_RESET_PORT &= ~DISPLAY_RESET_MASK;
	delay(10);
	DISPLAY_RESET_PORT |= DISPLAY_RESET_MASK;
	delay(10);

	spi_send_recv(0x8D);
	spi_send_recv(0x14);

	spi_send_recv(0xD9);
	spi_send_recv(0xF1);

	DISPLAY_VBATT_PORT &= ~DISPLAY_VBATT_MASK;
	delay(10000000);

	spi_send_recv(0xA1);
	spi_send_recv(0xC8);

	spi_send_recv(0xDA);
	spi_send_recv(0x20);

	spi_send_recv(0xAF);
}

void display_string(int line, char *s) {
	int i;
	if(line < 0 || line >= 4)
		return;
	if(!s)
		return;

	for(i = 0; i < 16; i++)
		if(*s) {
			textbuffer[line][i] = *s;
			s++;
		} else
			textbuffer[line][i] = ' ';
}

void display_update() {
	int i, j, k;
	int c;
	for(i = 0; i < 4; i++) {
		DISPLAY_COMMAND_DATA_PORT &= ~DISPLAY_COMMAND_DATA_MASK;
		spi_send_recv(0x22);
		spi_send_recv(i);

		spi_send_recv(0x0);
		spi_send_recv(0x10);

		DISPLAY_COMMAND_DATA_PORT |= DISPLAY_COMMAND_DATA_MASK;

		for(j = 0; j < 16; j++) {
			c = textbuffer[i][j];
			if(c & 0x80)
				continue;

			for(k = 0; k < 8; k++)
				spi_send_recv(font[c*8 + k]);
		}
	}
}

int getsw( void ){

	int SW = (PORTD >> 8) & 0x000F;

	return SW;
}

int getbtns(void){

	int BTN = (PORTD >> 5) & 0x0007;

	return BTN;
}

/* Wait for I2C bus to become idle */
void i2c_idle() {
	while(I2C1CON & 0x1F || I2C1STAT & (1 << 14)); //TRSTAT
}

/* Send one byte on I2C bus, return ack/nack status of transaction */
bool i2c_send(uint8_t data) {
	i2c_idle();
	I2C1TRN = data;
	i2c_idle();
	return !(I2C1STAT & (1 << 15)); //ACKSTAT
}

/* Receive one byte from I2C bus */
uint8_t i2c_recv() {
	i2c_idle();
	I2C1CONSET = 1 << 3; //RCEN = 1
	i2c_idle();
	I2C1STATCLR = 1 << 6; //I2COV = 0
	return I2C1RCV;
}

/* Send acknowledge conditon on the bus */
void i2c_ack() {
	i2c_idle();
	I2C1CONCLR = 1 << 5; //ACKDT = 0
	I2C1CONSET = 1 << 4; //ACKEN = 1
}

/* Send not-acknowledge conditon on the bus */
void i2c_nack() {
	i2c_idle();
	I2C1CONSET = 1 << 5; //ACKDT = 1
	I2C1CONSET = 1 << 4; //ACKEN = 1
}

/* Send start conditon on the bus */
void i2c_start() {
	i2c_idle();
	I2C1CONSET = 1 << 0; //SEN
	i2c_idle();
}

/* Send restart conditon on the bus */
void i2c_restart() {
	i2c_idle();
	I2C1CONSET = 1 << 1; //RSEN
	i2c_idle();
}

/* Send stop conditon on the bus */
void i2c_stop() {
	i2c_idle();
	I2C1CONSET = 1 << 2; //PEN
	i2c_idle();
}

/* Convert 8.8 bit fixed point to string representation*/
char *fixed_to_string(uint32_t num, char *buf) {
	bool neg = false;
	uint32_t n;
	char *tmp;

	if(num & 0x8000) {
		num = ~num + 1;
		neg = true;
	}
	buf += 4;
	n = num >> 8;
	tmp = buf;
	do {
		*--tmp = (n  % 10) + '0';
		n /= 10;
	} while(n);
	if(neg)
		*--tmp = '-';

	n = num;
	if(!(n & 0xFF)) {
		*buf = 0;
		return tmp;
	}
	*buf++ = '.';
	while((n &= 0xFF)) {
		n *= 10;
		*buf++ = (n >> 8) + '0';
	}
	*buf = 0;
	return tmp;
}

uint32_t strlen2(char *str) {
	uint32_t n = 0;
	while(*str++)
		n++;
	return n;
}

void tick( unsigned int * timep )
{
  /* Get current value, store locally */
  register unsigned int t = * timep;
  t += 1; /* Increment local copy */

  /* If result was not a valid BCD-coded time, adjust now */

  if( (t & 0x0000000f) >= 0x0000000a ) t += 0x00000006;
  if( (t & 0x000000f0) >= 0x00000060 ) t += 0x000000a0;
  /* Seconds are now OK */

  if( (t & 0x00000f00) >= 0x00000a00 ) t += 0x00000600;
  if( (t & 0x0000f000) >= 0x00006000 ) t += 0x0000a000;
  /* Minutes are now OK */

  if( (t & 0x000f0000) >= 0x000a0000 ) t += 0x00060000;
  if( (t & 0x00ff0000) >= 0x00240000 ) t += 0x00dc0000;
  /* Hours are now OK */

  if( (t & 0x0f000000) >= 0x0a000000 ) t += 0x06000000;
  if( (t & 0xf0000000) >= 0xa0000000 ) t = 0;
  /* Days are now OK */

  * timep = t; /* Store new value */
}

void itoa(int n, char s[])
 {
     int i, sign;

     if ((sign = n) < 0)
         n = -n;
     i = 0;
     do {
         s[i++] = n % 10 + '0';
     } while ((n /= 10) > 0);
     if (sign < 0)
         s[i++] = '-';
     s[i] = '\0';
     reverse(s);
 }

 void reverse(char s[])
 {
     int i, j;
     char c;

     for (i = 0, j = my_strlen(s)-1; i<j; i++, j--) {
         c = s[i];
         s[i] = s[j];
         s[j] = c;
     }
 }

int my_strlen(char *string)
{
int length;
for (length = 0; *string != '\0', string++)
{
length++;
}
return(length);
}

int main(void) {
	uint32_t temp;
	uint32_t temp2 = 0x00000000;
	uint32_t count = 0;
	int mytime = 0x0000;
	int presses1 = 0, presses2 = 0, presses3 = 0, presses4 = 0;
	int position = 1;
	int second = 0;
	int seconds = 0;
	uint32_t min = 0x00111100;
	uint32_t max = 0;
	bool read = true;
	char buf[32], buf1[32], buf2[32], *s, *t, *s1, *t1, *s2, *t2;

	volatile int * E = (volatile int *) 0xbf886100;
	volatile int * portE = (volatile int *) 0xbf886110;
	* portE = 0x0;
	* portE = * portE + 0x1;
	* E = * E & 0xFF00;
	TRISD = TRISD & 0x0FE0;

	/*Set up timer */
	PR2 = TMR2PERIOD;
	T2CONSET = 0x70;
	TMR2 = 0;
	T2CONSET = 0x8000;

	/* Set up peripheral bus clock */
	OSCCONCLR = 0x100000;
	OSCCONSET = 0x080000;

	/* Set up output pins */
	AD1PCFG = 0xFFFF;
	ODCE = 0x0;
	TRISECLR = 0xFF;
	PORTE = 0x0;

	/* Output pins for display signals */
	PORTF = 0xFFFF;
	PORTG = (1 << 9);
	ODCF = 0x0;
	ODCG = 0x0;
	TRISFCLR = 0x70;
	TRISGCLR = 0x200;

	/* Set up input pins */
	TRISDSET = (1 << 8);
	TRISFSET = (1 << 1);

	/* Set up SPI as master */
	SPI2CON = 0;
	SPI2BRG = 4;

	/* Clear SPIROV*/
	SPI2STATCLR = 0x40;
	/* Set CKP = 1, MSTEN = 1; */
    SPI2CONSET |= 0x40;

	/* Turn on SPI */
	SPI2CONSET = 0x20;

	SPI2CONSET = 0x8000;

	/* Set up i2c */
	I2C1CON = 0x0;
	/* I2C Baud rate should be less than 400 kHz, is generated by dividing
	the 40 MHz peripheral bus clock down */
	I2C1BRG = 0x0C2;
	I2C1STAT = 0x0;
	I2C1CONSET = 1 << 13; //SIDL = 1
	I2C1CONSET = 1 << 15; // ON = 1
	temp = I2C1RCV; //Clear receive buffer

	/* Set up input pins */
	TRISDSET = (1 << 8);
	TRISFSET = (1 << 1);


	display_init();
	display_string(0, "Temperature");
	display_string(1, "");
	display_string(2, "");
	display_string(3, "");
	display_update();

	/* Send start condition and address of the temperature sensor with
	write mode (lowest bit = 0) until the temperature sensor sends
	acknowledge condition */
	do {
		i2c_start();
	} while(!i2c_send(TEMP_SENSOR_ADDR << 1));
	/* Send register number we want to access */
	i2c_send(TEMP_SENSOR_REG_CONF);
	/* Set the config register to 0 */
	i2c_send(0x0);
	/* Send stop condition */
	i2c_stop();

	for(;;) {
		/* Send start condition and address of the temperature sensor with
		write flag (lowest bit = 0) until the temperature sensor sends
		acknowledge condition */
		do {
			i2c_start();
		} while(!i2c_send(TEMP_SENSOR_ADDR << 1));
		/* Send register number we want to access */
		i2c_send(TEMP_SENSOR_REG_TEMP);

		/* Now send another start condition and address of the temperature sensor with
		read mode (lowest bit = 1) until the temperature sensor sends
		acknowledge condition */
		do {
			i2c_start();
		} while(!i2c_send((TEMP_SENSOR_ADDR << 1) | 1));

		/* Now we can start receiving data from the sensor data register */

        temp = i2c_recv() << 8;
        i2c_ack();
        temp |= i2c_recv();

        int SW = getsw();

        if(SW == 2 || SW == 10) //fahrenheit
        {
            temp = (temp * 9/5) + 0x00002000;
        }
        if(SW == 1 || SW == 9) //kelvin
        {
            temp = temp + 0x00011100;
        }
        if(SW == 8 || SW == 10 || SW == 9)
        {
            if(IFS(0) & 0x100)
            {
                timeoutcount++;
                IFSCLR(0) = 0x100;
            }
            if(seconds > 0 && timeoutcount == 10)
            {
            temp2 += temp;
            if(temp < min && count > 1){min = temp;}
            if(temp > max && count > 1){max = temp;}
            tick(&mytime);
            count++;
            temp = temp2 / count;
            seconds--;
            timeoutcount = 0;
            }
            if(seconds <= 0)
            {
                read = false;
            }
        }

		/* To stop receiving, send nack and stop */
		i2c_nack();
		i2c_stop();

		s = fixed_to_string(temp, buf);
        t = s + strlen2(s);

		s1 = fixed_to_string(min, buf1);
		t1 = s1 + strlen2(s1);

        s2 = fixed_to_string(max, buf2);
		t2 = s2 + strlen2(s2);
	if(SW == 15){second = 0; seconds = 0; min = 0x00111100; max = 0; count = 0;}
        if(SW == 8 || SW == 12 || SW == 10 || SW == 9)
        {
            *t++ = ' ';
            *t++ = 'A';
            *t++ = 'V';
            *t++ = 'G';
            *t++ = 0;
            *t1++ = ' ';
            *t1++ = 'M';
            *t1++ = 'I';
            *t1++ = 'N';
            *t1++ = 0;
            *t2++ = ' ';
            *t2++ = 'M';
            *t2++ = 'A';
            *t2++ = 'X';
            *t2++ = 0;
            if(read)
            {
                display_string(1, s);
            }
            read = true;
            display_string(2, s1);
            display_string(3, s2);
        }
        if(SW == 4) //celcius
        {
            *t++ = ' ';
            *t++ = 7;
            *t++ = 'C';
            *t++ = 0;
            display_string(1, s);
        }
		if(SW == 2) //fahrenheit
        {
            *t++ = ' ';
            *t++ = 7;
            *t++ = 'f';
            *t++ = 0;
            display_string(1, s);
        }
        if(SW == 1) //kelvin
        {
            *t++ = ' ';
            *t++ = 7;
            *t++ = 'K';
            *t++ = 0;
            display_string(1, s);
        }
        if(SW == 0)
	   {
            s = "Input time:";
            int btn = getbtns();
            switch (btn)
            {
            case 1:
            {
                switch(position)
                case 1:
                {
                    if(presses1 != 9)
                    {
                        seconds += 1;
                        second += 0x0001;
                        presses1++;
                    }
                    break;
                }
                case 2:
                {
                    if(presses2 != 9)
                    {
                        seconds += 10;
                        second += 0x0010;
                        presses2++;
                    }
                    break;
                }
                case 3:
                {
                    if(presses3 != 9)
                    {
                        seconds += 100;
                        seconds += 0x0100
                        presses3++;
                    }
                    break;
                case 4:
                {
                    if(presses4 != 9)
                    {
                        seconds += 1000;
                        seconds += 0x01000;
                        presses4++;
                    }
                    break;
                }
            }
            case 2:
            {
                switch(position)
                case 1:
                {
                    if(presses1 != 0)
                    {
                        seconds -= 1;
                        second -= 0x0001;
                        presses1--;
                    }
                    break;
                }
                case 2:
                {
                    if(presses2 != 0)
                    {
                        seconds -= 10;
                        second -= 0x0010;
                        presses2--;
                    }
                    break;
                }
                case 3:
                {
                    if(presses3 != 0)
                    {
                        seconds -= 100;
                        seconds -= 0x0100
                        presses3--;
                    }
                    break;
                case 4:
                {
                    if(presses4 != 0)
                    {
                        seconds -= 1000;
                        seconds -= 0x01000;
                        presses4--;
                    }
                    break;
                }
            }
            case 4:
            {
                if(position != 4)
                {
                    position++;
                }
                else
                {
                    position = 1;
                }
                break;
            }
            }
            time2string( s1, seconds );
	        t1 = s1 + my_strlen(s1);
            *t1++ = ' ';
            *t1++ = 'S';
            *t1++ = 'E';
            *t1++ = 'C';
            *t1++ = 0;
            display_string(1, s);
            display_string(2, s1);
	   }
		display_update();
		delay(1000000);
	}

	return 0;
}
