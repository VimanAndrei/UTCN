library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity mem_date is
    Generic (DIM_MD : INTEGER := 256);          -- dimensiunea memoriei de date (cuvinte)
    Port ( Clk      : in  STD_LOGIC;
           Rst      : in  STD_LOGIC;
           WE       : in  STD_LOGIC;
           Adr      : in  STD_LOGIC_VECTOR (7 downto 0);
           Din      : in  STD_LOGIC_VECTOR (31 downto 0);
           Dout     : out STD_LOGIC_VECTOR (31 downto 0));
end mem_date;

architecture Behavioral of mem_date is
    type MD_TYPE is array (0 to DIM_MD-1) of STD_LOGIC_VECTOR (31 downto 0);
    signal MD : MD_TYPE := (others=>(others=>'0'));
begin
    process (Clk)
    begin
        if RISING_EDGE (Clk) then
            if (WE = '1') then
                MD (CONV_INTEGER (Adr)) <= Din;
            else
                if (Rst = '1') then
                    Dout <= (others => '0');
                else
                    Dout <= MD (CONV_INTEGER (Adr));
                end if;
            end if;
        end if;
    end process;
end Behavioral;
