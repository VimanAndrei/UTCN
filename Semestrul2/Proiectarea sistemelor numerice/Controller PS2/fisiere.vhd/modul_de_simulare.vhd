library ieee;
use ieee.std_logic_1164.all;

entity modul_de_simulare is
	
end  modul_de_simulare;   


architecture  modul_de_simulare of  modul_de_simulare is
   component top_ps2 is
	port(
	clk50: in std_logic;
	clr:in std_logic;
	PS2C:in std_logic;
	PS2D:in std_logic;
	a_to_g: out std_logic_vector(6 downto 0);
	anode: out std_logic_vector(3 downto 0)	
	);
end component; 
signal clk,reset,c,d:std_logic;
signal numarator:std_logic_vector(10 downto 0)	;
begin 
UST: top_ps2 port map (clk,reset,c,d);
reset<='0','1' after 5 ns,'0' after 10 ns;
ceas:process(clk)
begin
	if clk='U'then clk<='0';
	else clk<=not (clk) after 5 ns;
		end if;
	end process ceas;
	
ceas2:process(c)
begin
	if c='U'then c<='0';
	else c<=not (c) after 10 ns;
		end if;
	end process ceas2; 
	--d<='0','1' after 80 ns ,'0' after 100 ns,'1' after 140 ns,'0'after 200 ns ,'1'after 220 ns,'0' after 240 ns,'1' after ;
	
end  modul_de_simulare;