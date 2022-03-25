library ieee;
use ieee.std_logic_1164.all; 
use ieee.std_logic_unsigned.all;

entity counter1 is 
	 port (
	 clrfrecv:in std_logic;
	 clkfrecv: in std_logic;
	 frecv25: out std_logic;
	 frecv10: out std_logic
	 
	 );
 end counter1;
 architecture counter1 of counter1 is
 signal count:std_logic_vector(11 downto 0);
 begin
	 
	  process(clkfrecv,clrfrecv)
	  begin
		  if clrfrecv = '1' then count <= (others => '0');
		  elsif clkfrecv'event and clkfrecv = '1' then
			  count <= count + 1;
			  		  end if;
	   end process;
	   frecv25<=count(0);
	   frecv10<=count(11);
 end counter1;