library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;


entity top_ps2 is
	port(
	clk50: in std_logic;
	clr:in std_logic;
	PS2C:in std_logic;
	PS2D:in std_logic;
	a_to_g: out std_logic_vector(6 downto 0);
	anode: out std_logic_vector(3 downto 0)	
	);
end top_ps2; 
architecture top_ps2 of top_ps2 is
component keyboard_control is
	port(
	clk25: in std_logic;
	clr:in std_logic;
	PS2C:in std_logic;
	PS2D:in std_logic;
	key1: out std_logic_vector(7 downto 0);
	key2: out std_logic_vector(7 downto 0);
	key3: out std_logic_vector(7 downto 0)
	);
end component; 	
component counter1 is 
	 port (
	 clrfrecv:in std_logic;
	 clkfrecv: in std_logic;
	 frecv25: out std_logic;
	 frecv10: out std_logic
	 
	 );
end component;
component control7seg is 
	 port (
	 clr:in std_logic;
	 a0:in std_logic_vector(6 downto 0);
	 a1:in std_logic_vector(6 downto 0);
	 a2:in std_logic_vector(6 downto 0);
	 a3:in std_logic_vector(6 downto 0);
	 clk10: in std_logic;
	 iesire: out std_logic_vector(6 downto 0);
	 iesire_anode: out std_logic_vector(3 downto 0)	 
	 );
end component; 
component decodificare_informatie is  
	port (
	key1: in std_logic_vector(7 downto 0);
	key2: in std_logic_vector(7 downto 0);
	key3: in std_logic_vector(7 downto 0);
	clr:in std_logic;
	iesi0: out std_logic_vector(6 downto 0); 
	iesi1: out std_logic_vector(6 downto 0);
	iesi2: out std_logic_vector(6 downto 0);
	iesi3: out std_logic_vector(6 downto 0)
	);	
end component;

signal clk25,clk10:std_logic;
signal key1,key2,key3:std_logic_vector(7 downto 0);
signal iesire0,iesire1,iesire2,iesire3:std_logic_vector(6 downto 0); 

begin
	a1:	counter1 port map (
	 clrfrecv=>clr,
	 clkfrecv=>clk50,
	 frecv25=>clk25,
	 frecv10=>clk10);
	 
	a2: keyboard_control port map(
		clk25=>clk25,
	    clr=>clr,
	    PS2C=>PS2C,
	    PS2D=>PS2D,
	    key1=>key1,
	    key2=>key2,
	    key3=>key3);
		
	a3:	decodificare_informatie port map( 
	key1=>key1,
	key2=>key2,
	key3=>key3,
	clr=>clr,
	iesi0=>iesire0,
	iesi1=>iesire1,
	iesi2=>iesire2,
	iesi3=>iesire3); 
	
	a4:control7seg port map (
	clr=>clr,
	 a0=>iesire0,
	 a1=>iesire1,
	 a2=>iesire2,
	 a3=>iesire3,
	 clk10=>clk10,
	 iesire=>a_to_g,
	 iesire_anode=>anode);	
end top_ps2;

	 
	
	
	
	
	