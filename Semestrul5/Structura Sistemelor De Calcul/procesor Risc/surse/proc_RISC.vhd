library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity proc_RISC is
    Generic (DIM_MI : INTEGER := 256;           -- dimensiunea memoriei de instructiuni (cuvinte)
             DIM_MD : INTEGER := 256);          -- dimensiunea memoriei de date (cuvinte)
    Port ( Clk      : in  STD_LOGIC;
           Rst      : in  STD_LOGIC;
           AdrInstr : out STD_LOGIC_VECTOR (31 downto 0);
           Instr    : out STD_LOGIC_VECTOR (31 downto 0);
           Data     : out STD_LOGIC_VECTOR (31 downto 0);
           RA       : out STD_LOGIC_VECTOR (31 downto 0);
           RB       : out STD_LOGIC_VECTOR (31 downto 0);
           F        : out STD_LOGIC_VECTOR (31 downto 0);
           ZF       : out STD_LOGIC;
           CF       : out STD_LOGIC);
end proc_RISC;

architecture Behavioral of proc_RISC is

-- Semnale pentru interconectarea modulelor
    signal sMUXA        : STD_LOGIC_VECTOR (31 downto 0) := (others => '0');
    signal sMUXB        : STD_LOGIC_VECTOR (31 downto 0) := (others => '0');
    signal sMUXC        : STD_LOGIC_VECTOR (31 downto 0) := (others => '0');
    signal sMUXD        : STD_LOGIC_VECTOR (31 downto 0) := (others => '0');
    signal sPC          : STD_LOGIC_VECTOR (31 downto 0) := (others => '0');
    signal PCplus       : STD_LOGIC_VECTOR (31 downto 0) := (others => '0');
    signal sMI          : STD_LOGIC_VECTOR (31 downto 0) := (others => '0');
    signal sPCIF        : STD_LOGIC_VECTOR (31 downto 0) := (others => '0');
    signal sPCID        : STD_LOGIC_VECTOR (31 downto 0) := (others => '0');
    signal sPCEX        : STD_LOGIC_VECTOR (31 downto 0) := (others => '0');
    signal sRCONST      : STD_LOGIC_VECTOR (31 downto 0) := (others => '0');
    signal sCONST      : STD_LOGIC_VECTOR (31 downto 0) := (others => '0');
    signal sRDoutA      : STD_LOGIC_VECTOR (31 downto 0) := (others => '0');
    signal sRDoutB      : STD_LOGIC_VECTOR (31 downto 0) := (others => '0');
    signal AdrSalt      : STD_LOGIC_VECTOR (31 downto 0) := (others => '0');
    signal sUAL         : STD_LOGIC_VECTOR (31 downto 0) := (others => '0');
    signal sMD          : STD_LOGIC_VECTOR (31 downto 0) := (others => '0');
    signal sFEX         : STD_LOGIC_VECTOR (31 downto 0) := (others => '0');
    signal sMDEX        : STD_LOGIC_VECTOR (31 downto 0) := (others => '0');
    signal sCCEX        : STD_LOGIC_VECTOR (1  downto 0) := (others => '0');
    signal sSGTE        : STD_LOGIC_VECTOR (31 downto 0) := (others => '0');
    signal sSLT         : STD_LOGIC_VECTOR (31 downto 0) := (others => '0');
    signal sRI : std_logic_vector (31 downto 0) := (others => '0');
    signal sRID : std_logic_vector (21 downto 0) := (others => '0');
    signal sRIDout : std_logic_vector (21 downto 0) := (others => '0');
    signal NxorV        : STD_LOGIC := '0';
    signal NxnorV       : STD_LOGIC := '0';
    signal V            : STD_LOGIC := '0';
    signal C            : STD_LOGIC := '0';
    signal N            : STD_LOGIC := '0';
    signal Z            : STD_LOGIC := '0';

-- Semnale de comanda
    signal RegWr        : STD_LOGIC := '0';
    signal MemWr        : STD_LOGIC := '0';
    signal OpUAL        : STD_LOGIC_VECTOR (3 downto 0) := (others => '0');
    signal Sh           : STD_LOGIC_VECTOR (4 downto 0) := (others => '0');
    signal MxA          : STD_LOGIC := '0';
    signal MxB          : STD_LOGIC := '0';
    signal MxC          : STD_LOGIC_VECTOR (1 downto 0) := (others => '0');
    signal MxD          : STD_LOGIC_VECTOR (1 downto 0) := (others => '0');
    signal AdrSA        : STD_LOGIC_VECTOR (3 downto 0) := (others => '0');
    signal AdrSB        : STD_LOGIC_VECTOR (3 downto 0) := (others => '0');
    signal AdrD         : STD_LOGIC_VECTOR (3 downto 0) := (others => '0');
    signal SelC         : STD_LOGIC := '0';
    signal RIDCSalt     : STD_LOGIC := '0';
    signal RIDSSalt     : std_logic_vector(1 downto 0) := (others => '0');

    signal RegWrDCDI : std_logic := '0';
    signal RegWrRID : std_logic := '0';
    signal MemWrDCDI : std_logic := '0';
    signal OpUALDCDI : std_logic_vector (3 downto 0) := (others => '0');
    signal MxADCDI : std_logic := '0';
    signal MxBDCDI : std_logic := '0';
    signal MxDDCDI : std_logic_vector (1 downto 0) := (others => '0');
    signal AdrDDCDI : std_logic_vector (3 downto 0) := (others => '0');
    signal SSaltDCDI : std_logic_vector(1 downto 0) := (others => '0');
    signal CSaltDCDI : std_logic := '0';
    signal SSalt : std_logic_vector(1 downto 0) := (others => '0');
    signal CSalt : std_logic := '0';
    
    signal EnInstr : std_logic := '0';
    signal Qout : std_logic := '1';
    
    signal sREX : std_logic_vector (6 downto 0) := (others => '0');
    signal sREXout : std_logic_vector (6 downto 0) := (others => '0');
    signal sNV : std_logic_vector (1 downto 0) := (others => '0');
    
-- Semnale pentru eliminarea hazardului de control prin predictia salturilor
    signal sMI_hazard   : STD_LOGIC_VECTOR (31 downto 0) := (others => '0');

begin
    MI:         entity WORK.mem_instr   port map (Clk => Clk, Rst => Rst, Adr => sPC (7 downto 0), Data => sMI);
    R:          entity WORK.set_reg     port map (Clk => Clk, Rst => Rst, WE => RegWr, AdrA => AdrSA, AdrB => AdrSB,
                                                  AdrD => AdrD, Din => sMUXD, DoutA => sRDoutA, DoutB => sRDoutB);
    UAL:        entity WORK.unit_aritm  port map (X => sMUXA, Y => sMUXB, Sel => OpUAL, Sh => Sh, F => sUAL, V => V, C => C, N => N, Z => Z);
    MD:         entity WORK.mem_date    port map (Clk => Clk, Rst => Rst, WE => MemWr, Adr => sMUXA (7 downto 0), Din => sMUXB, Dout => sMD);
    
    CONST:      entity WORK.CONST port map(Clk=>Clk, SelC => SelC, input => sRI(15 downto 0), output => sCONST );
    
    SELMUXC:    entity WORK.SELMUXC port map(SSalt => RIDSSalt, CSalt => RIDCSalt,Z => Z ,MxC => MxC );
    
    DCDI : entity WORK.DCDI port map(Instr => sRI, RegWr => RegWrDCDI, AdrD => AdrDDCDI, MxD => MxDDCDI, SSalt => SSaltDCDI, CSalt => CSaltDCDI, MemWr => MemWrDCDI, OpUAL => OpUALDCDI, MxA => MxADCDI, MxB => MxBDCDI, AdrSA => AdrSA, AdrSB => AdrSB, SelC => SelC);
    
    sRID <= (RegWRDCDI and EnInstr) & AdrDDCDI & MxDDCDI & (SSaltDCDI(1) and EnInstr) & (SSaltDCDI(0) and EnInstr) & CSaltDCDI & (MemWrDCDI and EnInstr) & OpUALDCDI & MxADCDI & MxBDCDI & sRI(4 downto 0);
       
    --registre
    PC : entity WORK.FDN port map ( D => sMUXC, CE => '1', Clk => Clk, Rst => Rst,  Q => sPC);
    PCIF : entity WORK.FDN port map ( D => sPC, CE => '1', Clk => Clk, Rst => Rst,  Q => sPCIF);
    PCID : entity WORK.FDN port map ( D => sPCIF, CE => '1', Clk => Clk, Rst => Rst,  Q => sPCID);
    PCEX : entity WORK.FDN port map ( D => sPCID, CE => '1', Clk => Clk, Rst => Rst,  Q => sPCEX);
    
    
     -- Bistabil
    process (Clk)
    begin
        if rising_edge(Clk) then
            if Rst = '1' then
                Qout <= '1';
            else
                Qout <= EnInstr;
            end if;
        end if;
    end process;
    
    hazard : for i in 0 to 31 generate
        sMI_hazard(i) <= sMI(i) and EnInstr and Qout;
    end generate hazard;

    RI : entity WORK.FDN port map ( D => sMI_hazard, CE => '1', Clk => Clk, Rst => Rst,  Q => sRI);
    RID : entity WORK.FDN generic map(n => 22) port map ( D => sRID, CE => '1', Clk => Clk, Rst => Rst,  Q => sRIDout);
    
    REX : entity WORK.FDN generic map(n => 7) port map (D => sREX,CE => '1',Clk => Clk, Rst => Rst,Q => sREXout);
    
    SSalt <= sRIDout(14 downto 13);
    CSalt <= sRIDout(12);
    MemWr <= sRIDout(11);
    OpUAL <= sRIDout(10 downto 7);
    MxA <= sRIDout(6);
    MxB <= sRIDout(5);
    Sh <= sRIDout(4 downto 0);
    sREX <= sRIDout(21 downto 15);
    RegWr <= sREXout(6);
    AdrD <= sREXout(5 downto 2);
    MxD <= sREXout(1 downto 0);
    
    RCONST : entity WORK.FDN port map ( D => sCONST, CE => '1', Clk => Clk, Rst => Rst,  Q => sRCONST);
         
    NxorV  <= N xor V;
    NxnorV <= not NxorV;
    sSLT   <= x"0000_000" & b"000" & sCCEX(0);
    sSGTE  <= x"0000_000" & b"000" & sCCEX(1);
    
    EnInstr <= MxC(0) nor MxC(1);

    INCPC:      PCplus <= sPC + 1;
    ADDPC:      AdrSalt <= sPCID + sMUXB;
    MUXA:       sMUXA <= sRDoutA when MxA = '0' else sPCID;
    MUXB:       sMUXB <= sRDoutB when MxB = '0' else sRCONST;
    MUXC:       with MxC select
                    sMUXC <= PCplus when "00", AdrSalt when "01", sMUXA when "10", sPCEX when others;
    MUXD:       with MxD select
                    sMUXD <= sFEX when "00", sMDEX when "01", sSGTE when "10", sSLT when others;
    
    sNV(1) <= not(N or V);
	sNV(0) <= N or V;
    CCEX : entity WORK.FDN generic map(n => 2) port map (D => sNV,CE => '1',Clk => Clk, Rst => Rst,Q => sCCEX);
    
    FEX : entity WORK.FDN port map (D => sUAL, CE => '1', Clk => Clk, Rst => Rst,  Q => sFEX);
    MDEX : entity WORK.FDN port map (D => sMD, CE => '1', Clk => Clk, Rst => Rst,  Q => sMDEX);

-- Conectarea semnalelor la porturile de iesire
    AdrInstr <= sPCIF;
    Instr    <= sMI_hazard;
    Data     <= sMD;
    RA       <= sMUXA;
    RB       <= sMUXB;
    F        <= sUAL;
    ZF       <= Z;
    CF       <= C;

end Behavioral;
