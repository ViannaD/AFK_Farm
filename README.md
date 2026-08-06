# AFK Farms — Fabric 1.21.1 (port do addon Bedrock)

Porte em andamento do addon Bedrock **"AFK Farms Add-On"** para Java Edition (Fabric,
Minecraft 1.21.1). Esta entrega é a **primeira etapa**: o framework genérico + a
**Fazenda de Trigo** completa, como prova de conceito. As outras 21 fazendas do addon
original entram depois, reaproveitando exatamente essa mesma base.

## ⚠️ Antes de tudo: preciso que você compile localmente

O ambiente onde eu trabalho não tem acesso aos repositórios Maven da Fabric/Mojang
(só PyPI, npm, GitHub, crates.io, apt). Ou seja: **escrevi todo o código com cuidado,
mas não consegui rodar `gradlew build` para confirmar que compila 100% sem erros.**
Revisei cada arquivo manualmente atrás de erros óbvios, mas você deve estar preparado
para pequenos ajustes de API ao abrir no seu PC (ver seção "Possíveis pontos de ajuste"
abaixo).

## Como compilar

1. Precisa de **JDK 21**.
2. Abra a pasta `afkfarms-fabric/` no IntelliJ IDEA (recomendado) ou VS Code com
   extensão Java.
3. Rode `./gradlew build` (Linux/Mac) ou `gradlew.bat build` (Windows). Na primeira vez,
   o Gradle/Loom vai baixar Minecraft, Yarn mappings, Fabric Loader e Fabric API —
   isso demora e precisa de internet liberada para `maven.fabricmc.net` e afins.
4. O `.jar` final aparece em `build/libs/afkfarms-1.0.0.jar`. Coloque na pasta `mods`
   de uma instância Fabric 1.21.1 (com [Fabric Loader](https://fabricmc.net/use/) e
   [Fabric API](https://modrinth.com/mod/fabric-api) instalados).

## CI (GitHub Actions)

O repositório já vem com `.github/workflows/build.yml`: builda automaticamente em
todo push/PR para `main`/`master`, e disponibiliza o `.jar` gerado como artifact do
workflow (aba "Actions" → o run → "Artifacts"). Como o `gradle-wrapper.jar` não está
commitado (ver nota acima), o workflow instala o Gradle 8.8 diretamente via
`gradle/actions/setup-gradle` em vez de depender do `./gradlew`. Depois que você
gerar o wrapper localmente e commitar o jar, pode trocar o step de build do workflow
de volta para `./gradlew build` se preferir.

> Nota: incluí os scripts `gradlew` / `gradlew.bat` (são só texto, sem problema), mas
> **falta o `gradle/wrapper/gradle-wrapper.jar`** — esse é um binário compilado que
> preciso baixar do servidor da Gradle, e meu sandbox não tem acesso a esse domínio.
> Rode `gradle wrapper --gradle-version 8.8` uma vez (com o Gradle instalado
> localmente) na raiz do projeto para gerá-lo, ou abra o projeto direto no IntelliJ
> com suporte a Loom, que resolve isso sozinho. O workflow do GitHub Actions
> (`.github/workflows/build.yml`) já contorna isso: ele instala o Gradle diretamente
> e roda `gradle build` em vez de `./gradlew build`, então o CI funciona mesmo sem o
> jar do wrapper no repositório.

## O que foi convertido e como

### O framework (reaproveitável para as outras 21 fazendas)

| Bedrock (addon original) | Java/Fabric (este mod) |
|---|---|
| Entidade "farm" (rideable, hit-test customizado) | `FarmBlock` — bloco normal com `facing`, sobrevive a reload sem gambiarra |
| `FarmingComponent` (scripts/actors/components/farming.js) | `FarmBlockEntity.collect()` — produção calculada por **tempo real** (relógio do sistema), com "catch-up" automático quando o chunk recarrega, igual ao original |
| `FarmFuelComponent` (fuel.js) | Mesma classe: campos `fuelSeconds` / `boostSeconds`, mesmos limites (`FUEL_CAP`, `BOOST_CAP`), mesma tabela de combustível (carvão/carvão vegetal/bloco de carvão) |
| `minecraft:inventory` (27 slots) + hopper chain customizada | `FarmBlockEntity implements Inventory` — compatível com hoppers **vanilla** direto, sem sistema próprio |
| `FARM_DATA_TABLE` (scripts/shared/lookup_tables.js) | `FarmType` / `FarmResource` / `FarmTypes` — mesma estrutura de dados (recursos, chance, xpGain, fuelCost, secondsRate, boostTable) |
| Menu de interação (abrir UI, alimentar item na mão) | `FarmBlock.onUseWithItem` — clique segurando combustível/boost alimenta direto; qualquer outro clique abre um inventário genérico (igual a um baú) |

### A Fazenda de Trigo (prova de conceito)

- **Geometria**: escrevi um conversor Python (`tools/convert_geo.py`, não incluído no
  jar, mas posso te mandar se quiser reusar para as outras fazendas) que leu o
  `farm_wheat.geo.json` original e converteu **os 153 cubos** (estrutura, moinho,
  6 fileiras de plantação com 4 estágios de crescimento cada, cerca de grade de ferro)
  para código Java (`WheatFarmModel.java`), aplicando a conversão de coordenadas
  Bedrock→Java (espelhamento no eixo X, mesma convenção usada por conversores como o
  Blockbench).
- **Textura**: copiei a textura 512×512 original (`textures/entity/farms/farm_wheat.png`)
  e o ícone do item (16×16) direto do addon.
- **Animação**: o moinho gira continuamente enquanto a fazenda está ativa, e cada
  fileira de trigo cicla pelos 4 estágios de crescimento — mesmo comportamento do
  clipe original (`idle.active`, ~4s de loop).
- **Produção**: 1 trigo (100%) + 1 semente de trigo (75% de chance) a cada 30s por
  unidade de combustível, boost com farinha de osso (+80s), exatamente os números do
  addon original.

### Simplificações conscientes (não é preguiça, é escolha de engenharia)

1. **Biblioteca de "blocks" compartilhada do Bedrock não foi portada 1:1.** Ela tem
   ~266 cubos extras só para desenhar ícones de item em "pixel art 3D" flutuando sobre
   o slot de boost, e um mostrador de dígitos também em geometria. Isso não tem
   equivalente direto no sistema de modelos do Java e agrega zero à jogabilidade — troquei
   por **texto flutuante simples** mostrando o tempo de combustível restante.
2. **Sistema de "blueprint + conversor" virou uma receita de crafting direta.** No
   addon original você craftava um blueprint genérico e "pagava" os materiais da
   fazenda em um menu conversor. Aqui isso virou uma receita shapeless simplificada
   (ver `data/afkfarms/recipes/wheat_farm.json`) — ajuste os ingredientes à vontade.
3. **GUI genérica em vez de UI customizada.** Uso a tela padrão de baú do Java (9x3)
   em vez de recriar os botões customizados do Bedrock — funcionalmente equivalente
   (ver e retirar itens), só visualmente mais simples.
4. **Eixo/velocidade exata da rotação do moinho** foi estimada (não importei os
   keyframes originais 1:1, já que o clipe do Bedrock é só uma "flutuação" decorativa
   dessincronizada da produção real) — ajuste `WheatFarmBlockEntityRenderer` se quiser
   afinar visualmente.

## Possíveis pontos de ajuste ao compilar

Como não consegui compilar aqui, esses são os pontos com maior chance de precisar de
um pequeno ajuste de nome de método (a lógica em si não muda):

- `Block.onUseWithItem(...)` — o nome/assinatura exato desse método mudou entre
  versões 1.20.x→1.21.x nos mappings Yarn; se o compilador reclamar, procure o método
  equivalente em `net.minecraft.block.Block` na sua versão do Yarn (ex.: pode estar
  como `onUse` com `ItemStack` incluso, dependendo do build exato do mapping).
- `NbtCompound.getLong/getBoolean/getInt/getCompound` — assumi que retornam o tipo
  primitivo direto (correto para 1.21.1); versões mais novas do Minecraft mudaram
  isso para `Optional`, então não deve dar problema aqui, mas fique de olho.
- `Model.render(...)` com 5 parâmetros (incluindo `color`) — confirme a assinatura
  exata na sua build do Yarn.

Nenhum desses afeta a lógica de jogo, só nomes/assinaturas de método da API do
Minecraft — são ajustes de "apontar o erro do compilador e corrigir o nome", não
reescrita.

## Próximos passos

Quando você validar que a Fazenda de Trigo compila e funciona do jeito que quer, sigo
com o resto:
- Repetir o processo (`convert_geo.py` + `FarmType` + subclasse de bloco) para as
  outras 21 fazendas (cenoura, batata, beterraba, cana, bambu, kelp, pedra, ouro,
  ferro, e as fazendas de mob: vaca, porco, ovelha, galinha, creeper, enderman,
  guardian, esqueleto, slime, aranha, witch).
- Portar os sistemas extras do addon (livro-guia, pet book, biocache, conversor) se
  você quiser esse escopo completo também.

Me avisa como foi a compilação e eu continuo a partir daí.
