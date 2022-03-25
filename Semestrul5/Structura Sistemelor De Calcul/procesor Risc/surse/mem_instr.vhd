library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity mem_instr is
    Generic (DIM_MI : INTEGER := 256);          -- dimensiunea memoriei de instructiuni (cuvinte)
    Port ( Clk      : in  STD_LOGIC;
           Rst      : in  STD_LOGIC;
           Adr      : in  STD_LOGIC_VECTOR (7 downto 0);
           Data     : out STD_LOGIC_VECTOR (31 downto 0));
end mem_instr;

architecture Behavioral of mem_instr is
    type MI_TYPE is array (0 to DIM_MI-1) of STD_LOGIC_VECTOR (31 downto 0);
    signal MI : MI_TYPE := (
                            x"0A00_0000",       -- 00 XOR  R0,R0,R0
                            x"2210_002D",       -- 01 ADDI R1,R0,45 (0x2D)
                            x"2220_0024",       -- 02 ADDI R2,R0,36 (0x24)
                            x"4030_0000",       -- 03 MOVA R3,R0
                            x"2240_0010",       -- 04 ADDI R4,R0,16 (0x10)
                            x"2852_0001",       -- 05 ANDI R5,R2,1
                            x"0000_0000",       -- 06 NOP
                            x"0000_0000",       -- 07 NOP

                            x"6005_0003",       -- 08 BZ   R5,3
                            x"0000_0000",       -- 09 NOP
                            x"0000_0000",       -- 0A NOP
                            x"0233_1000",       -- 0B ADD  R3,R3,R1						
                            x"0E11_0001",       -- 0C SHL  R1,R1,1
                            x"0D22_0001",       -- 0D SHR  R2,R2,1
                            x"2544_0001",       -- 0E SUBI R4,R4,1
                            x"0000_0000",       -- 0F NOP

                            x"0000_0000",       -- 10 NOP
                            x"5004_FFF3",       -- 11 BNZ  R4,-13 (0xFFF3)
                            x"4003_0000",       -- 12 MOVA R0,R3
                            x"6900_0000",       -- 13 HALT
                            x"6900_0000",       -- 14 HALT
                            x"6900_0000",       -- 15 HALT
                            x"6900_0000",       -- 16 HALT
                            x"6900_0000",       -- 17 HALT

                            x"6900_0000",       -- 18 HALT
                            x"6900_0000",       -- 19 HALT
                            x"6900_0000",       -- 1A HALT
                            x"6900_0000",       -- 1B HALT
                            x"6900_0000",       -- 1C HALT
                            x"6900_0000",       -- 1D HALT
                            x"6900_0000",       -- 1E HALT
                            x"6900_0000",       -- 1F HALT

                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 20 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 24 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 28 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 2C HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 30 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 34 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 38 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 3C HALT

                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 40 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 44 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 48 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 4C HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 50 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 54 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 58 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 5C HALT

                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 60 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 64 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 68 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 6C HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 70 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 74 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 78 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 7C HALT

                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 80 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 84 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 88 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 8C HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 90 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 94 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 98 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- 9C HALT

                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- A0 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- A4 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- A8 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- AC HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- B0 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- B4 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- B8 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- BC HALT

                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- C0 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- C4 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- C8 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- CC HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- D0 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- D4 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- D8 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- DC HALT

                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- E0 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- E4 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- E8 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- EC HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- F0 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- F4 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000",     -- F8 HALT
                            x"6900_0000", x"6900_0000", x"6900_0000", x"6900_0000");    -- FC HALT

    begin
        process (Clk)
        begin
            if RISING_EDGE (Clk) then
                if (Rst = '1') then
                    Data <= (others => '0');
                else
                    Data <= MI (CONV_INTEGER (Adr));
                end if;
            end if;
        end process;
    end Behavioral;
