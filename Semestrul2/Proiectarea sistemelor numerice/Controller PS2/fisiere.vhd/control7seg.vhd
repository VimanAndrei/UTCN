library ieee;
use ieee.std_logic_1164.all; 
use ieee.std_logic_unsigned.all;

entity control7seg is 
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
end control7seg; 

architecture control7seg of control7seg is 

component mux41 is
	port (
	amux41: in std_logic_vector(27 downto 0);
	smux41: in std_logic_vector(1 downto 0);
	ymux41: out std_logic_vector(6 downto 0)
	);
end component; 

component counter is 
	 port (
	 clrcounter:in std_logic;
	 clkcounter: in std_logic;
	 qcounter: out std_logic_vector(1 downto 0)
	 );
end component;	 


component decoder is 
	 port (
	 decodein:in std_logic_vector(1 downto 0);
	 decodeout: out std_logic_vector(3 downto 0)
	 );
end component;

signal numarare: std_logic_vector(1 downto 0);
begin
	
	x1:counter port map( 
	clrcounter=>clr,
	clkcounter=>clk10,
	qcounter=>numarare);
	
	x2:decoder port map(
	decodein=>numarare,
	decodeout=>iesire_anode);
	
	x3:mux41 port map(
	smux41=>numarare,
	amux41(6 downto 0)=>a0,
	amux41(13 downto 7)=>a1,
	amux41(20 downto 14)=>a2,
	amux41(27 downto 21)=>a3,
	ymux41=>iesire);
end  control7seg;

-- daca nu merge vezi porturile ca poate nu sunt legate bine	
	
	
	




