library IEEE;
use IEEE.STD_LOGIC_1164.ALL;


entity CONST is
  Port ( 
  Clk: in std_logic;
  SelC: in std_logic;
  input: in std_logic_vector(15 downto 0);  
  output: out std_logic_vector(31 downto 0)
  );
end CONST;

architecture Behavioral of CONST is

begin
process(Clk, SelC, input)
begin
    if rising_edge(Clk) then
        if SelC = '0' then
            output <= "0000000000000000" & input;
        elsif input(15) = '1' then
            output <= "1111111111111111" & input ;
           else
            output <= "0000000000000000" & input ;
        end if;
     end if;
end process;


end Behavioral;
