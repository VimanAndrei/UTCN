library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
use WORK.RISC_pkg.ALL;

entity FUNC is
    Port ( X   : in  STD_LOGIC_VECTOR (31 downto 0);
           Y   : in  STD_LOGIC_VECTOR (31 downto 0);
           Sel : in  STD_LOGIC_VECTOR (3 downto 0);
           E   : out STD_LOGIC_VECTOR (31 downto 0);
           V   : out STD_LOGIC;
           C   : out STD_LOGIC);
end FUNC;

architecture Behavioral of FUNC is
    signal Tmp : STD_LOGIC_VECTOR (32 downto 0) := (others => '0');
begin
    process (X, Y, Sel)
    begin
        case Sel is
            when FX =>
                Tmp <= '0' & X;
            when FADD =>
                Tmp <= (X(31) & X) + (Y(31) & Y);
            when FSUB =>
                Tmp <= (X(31) & X) - (Y(31) & Y);
            when FANDC =>
                Tmp <= ('0' & X) and ('0' & Y);
            when FORC =>
                Tmp <= ('0' & X) or ('0' & Y);
            when FXORC =>
                Tmp <= ('0' & X) xor ('0' & Y);
            when FNOTC =>
                Tmp <= not ('0' & X);
            when FY =>
                Tmp <= '0' & Y;
            when others =>
                Tmp <= '0' & X;
        end case;
end process;

    E <= Tmp (31 downto 0);
    with Sel select
        V <= Tmp(32) xor Tmp(31) when FADD | FSUB,
             '0' when others;
    with Sel select
        C <= Tmp(32) when FADD | FSUB,
             '0' when others;
end Behavioral;