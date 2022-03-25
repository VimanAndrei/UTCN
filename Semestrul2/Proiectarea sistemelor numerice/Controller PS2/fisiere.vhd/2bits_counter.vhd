library ieee;
use ieee.std_logic_1164.all; 
use ieee.std_logic_unsigned.all;

entity counter is 
	 port (
	 clrcounter:in std_logic;
	 clkcounter: in std_logic;
	 qcounter: out std_logic_vector(1 downto 0)
	 );
 end counter;
 architecture counter of counter is
 signal count:std_logic_vector(1 downto 0);
 begin
	 
	  process(clkcounter,clrcounter)
	  begin
		  if clrcounter = '1' then count <= (others => '0');
		  elsif clkcounter'event and clkcounter = '1' then
			  count <= count + 1;
			  		  end if;
	   end process;
	  qcounter<=count;
 end counter;