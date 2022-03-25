library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use ieee.std_logic_arith.all;
use ieee.std_logic_unsigned.all;


entity sumator_nbiti is
  GENERIC ( n : natural ) ;
  Port (
    x: in std_logic_vector(n-1 downto 0);
    y: in std_logic_vector(n-1 downto 0);
    tin: in std_logic;
    tout: out std_logic;
    ovf: out std_logic;
    s: out std_logic_vector(n-1 downto 0)
   );
end sumator_nbiti;

architecture Behavioral of sumator_nbiti is

begin				  
process(X,Y,Tin)
variable carry:std_logic_vector(n downto 0);
begin
       carry(0) := Tin;
		for i in 0 to n-1 loop
			S(i) <= X(i) xor Y(i) xor carry(i);
			carry(i+1) := (X(i) and Y(i)) or ((X(i) or Y(i)) and carry(i));
		end loop;
		tout<=carry(n);
		ovf<=carry(n) xor carry(n-1);
end process;
end Behavioral;