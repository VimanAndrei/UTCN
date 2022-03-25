----------------------------------------------------------------------------------
-- Company:         UTCN
-- Engineer: 
-- 
-- Create Date:     05/03/2016 08:36:25 PM
-- Design Name:     afis_uart
-- Module Name:     afis_uart - Behavioral
-- Project Name:    implem_RISC
-- Target Devices: 
-- Tool Versions:   Vivado 2015.4, 2016.4
-- Description:     Modul de conversie a iesirilor procesorului RISC in formatul
--                  necesar pentru afisare si de transmisie a datelor convertite
--                  pe interfata seriala UART
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------

library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity afis_uart is
    Port ( Clk      : in STD_LOGIC;
           Rst      : in STD_LOGIC;
           AdrInstr : in STD_LOGIC_VECTOR (31 downto 0);
           Instr    : in STD_LOGIC_VECTOR (31 downto 0);
           RA       : in STD_LOGIC_VECTOR (31 downto 0);
           RB       : in STD_LOGIC_VECTOR (31 downto 0);
           F        : in STD_LOGIC_VECTOR (31 downto 0);
           Send     : in STD_LOGIC;
           Tx       : out STD_LOGIC;
           Rdy      : out STD_LOGIC);
end afis_uart;

architecture Behavioral of afis_uart is
    signal Data1    : STD_LOGIC_VECTOR (63 downto 0) := (others => '0');
    signal Data2    : STD_LOGIC_VECTOR (63 downto 0) := (others => '0');
    signal Data3    : STD_LOGIC_VECTOR (63 downto 0) := (others => '0');
    signal Data4    : STD_LOGIC_VECTOR (63 downto 0) := (others => '0');
    signal Data5    : STD_LOGIC_VECTOR (63 downto 0) := (others => '0');
    signal Data6    : STD_LOGIC_VECTOR (63 downto 0) := (others => '0');
    signal Data7    : STD_LOGIC_VECTOR (63 downto 0) := (others => '0');
    signal Data8    : STD_LOGIC_VECTOR (63 downto 0) := (others => '0');
    
begin

    conv_uart_i:    entity WORK.conv_uart port map (
                        AdrInstr => AdrInstr,
                        Instr => Instr,
                        RA => RA,
                        RB => RB,
                        F  => F,
                        Data1 => Data1,
                        Data2 => Data2,
                        Data3 => Data3,
                        Data4 => Data4,
                        Data5 => Data5,
                        Data6 => Data6,
                        Data7 => Data7,
                        Data8 => Data8);

    uart_send64_i:  entity WORK.uart_send64 port map (
                        Clk => Clk,
                        Rst => Rst,
                        Data1 => Data1,
                        Data2 => Data2,
                        Data3 => Data3,
                        Data4 => Data4,
                        Data5 => Data5,
                        Data6 => Data6,
                        Data7 => Data7,
                        Data8 => Data8,
                        Send => Send,
                        Tx => Tx,
                        Rdy => Rdy);

end Behavioral;
