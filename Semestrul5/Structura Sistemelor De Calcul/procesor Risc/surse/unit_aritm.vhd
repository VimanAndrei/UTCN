library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
use WORK.RISC_pkg.ALL;

entity unit_aritm is
    Port ( X   : in  STD_LOGIC_VECTOR (31 downto 0);
           Y   : in  STD_LOGIC_VECTOR (31 downto 0);
           Sel : in  STD_LOGIC_VECTOR (3 downto 0);
           Sh  : in  STD_LOGIC_VECTOR (4 downto 0);
           F   : out STD_LOGIC_VECTOR (31 downto 0);
           V   : out STD_LOGIC;
           C   : out STD_LOGIC;
           N   : out STD_LOGIC;
           Z   : out STD_LOGIC);
end unit_aritm;

architecture Behavioral of unit_aritm is
    signal sFUNC : STD_LOGIC_VECTOR (31 downto 0) := (others => '0');   -- iesire modul FUNC
    signal sDEPL : STD_LOGIC_VECTOR (31 downto 0) := (others => '0');   -- iesire modul DEPL
    signal sUAL  : STD_LOGIC_VECTOR (31 downto 0) := (others => '0');   -- iesire UAL
begin

    FUNC_i: entity WORK.FUNC port map (X => X, Y => Y, Sel => Sel, E => sFUNC, V => V, C => C);
    DEPL_i: entity WORK.DEPL port map (D => X, Sel => Sel, Sh => Sh, SRI => '0', SLI => '0', G => sDEPL);

    with Sel select                                                     -- selectia iesirii UAL
        sUAL <= sDEPL when FSHR | FSHL,
                sFUNC when others;
    F <= sUAL;
    N <= sUAL(31);
    Z <= '1' when sUAL = x"0000_0000" else '0';

end Behavioral;
