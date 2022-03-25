library IEEE;
use IEEE.STD_LOGIC_1164.ALL;


entity FDN is
  GENERIC ( n : INTEGER := 32 ) ;
  Port (
    d: in std_logic_vector(n-1 downto 0);
    ce: in std_logic;
    clk: in std_logic;
    rst: in std_logic;
    q: out std_logic_vector(n-1 downto 0)
   );
end FDN;

architecture Behavioral of FDN is

begin
registru: process(d,ce,rst,clk)
begin
    if rising_edge(clk) then 
        if rst = '1' then 
            q <= (others => '0');
         elsif ce = '1' then 
            q <= d;
        end if;
        
    end if;
end process registru;


end Behavioral;