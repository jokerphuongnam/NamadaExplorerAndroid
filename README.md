# Namada Explorer

Namada Explorer is an Android application designed to explore the Namada blockchain network. It allows users to view essential blockchain data and navigate through different screens to access specific information conveniently.

## Installation

You can install Namada Explorer by downloading the provided APK file from the repository and installing it on your Android device.

https://github.com/jokerphuongnam/NamadaExplorerAndroid/assets/44250248/80acd037-b51d-4741-ab10-9bff5d93b465

## Features

### Navigation

The app features a user-friendly navigation system, enabling users to move between different screens:

- **Main Screen**: Users can navigate to:
 - [Home Screen](#home-screen)
 - [Validators Screen](#validators-screen)
 - [Blocks Screen](#blocks-screen)
 - [Transactions Screen](#transactions-screen)
 - [Governance Screen](#governance-screen)
 - [Parameters Screen](#parameters-screen)
  
Additionally, users can access external web links:
  - [Namada Shielded Expedition NEBB](https://namada.net/shielded-expedition)

## Home Screen <a name="home-screen"></a>

Displays essential statistics such as epoch, block height, total stake, validators, Governance Proposals, and Chain ID. It also showcases the top 10 validators and top 10 blocks.

## Validators Screen <a name="validators-screen"></a>

Provides statistics and a comprehensive list of all validators.

## Blocks Screen <a name="blocks-screen"></a>

Shows a list of all blocks on the blockchain.

## Transactions Screen <a name="transactions-screen"></a>

Displays transfer and bond transactions.

## Governance Screen <a name="governance-screen"></a>

Shows proposals related to governance.

## Parameters Screen <a name="parameters-screen"></a>

Provides information on various parameters including Genesis Parameters, Chain Parameters, Proof of Stake Parameters, Governance Parameters, and Genesis Validators.

### Details

Users can click on items from validators, blocks, transactions, proposals, and Genesis Validators to view detailed information. If the item is a block, users can click on the validator's address to go to the Validator Detail Screen.

- **Validator Detail Screen**: Displays detailed information about a validator and the latest 10 blocks from the validator.

## Demo
https://github.com/jokerphuongnam/NamadaExplorerAndroid/assets/44250248/fcf2bc75-0381-41ad-ac2e-b184fd0a0ee9

## Screenshots
 - ### Main
  - Main navigation draw menu
   ![Main-navigation-draw-menu](https://github.com/jokerphuongnam/NamadaExplorerAndroid/assets/44250248/234d5b60-37d0-427d-a749-e2d577c8070f)
   
 - ### Home
  - Details
   ![Home-Detail](https://github.com/jokerphuongnam/NamadaExplorerAndroid/assets/44250248/82d0912a-c103-4e07-9c4d-3019bbba1673)
  - Top 10 Validators 
   ![Home-top-10-validators](https://github.com/jokerphuongnam/NamadaExplorerAndroid/assets/44250248/72e86110-897f-40a1-b390-a981379a55a7)
  - Top 10 Blocks
   ![Home-top-10-blocks](https://github.com/jokerphuongnam/NamadaExplorerAndroid/assets/44250248/5a602585-a6b1-4651-8671-6e610be1bf80)
   
 - ### Validators
  - Validators
   ![Validators](https://github.com/jokerphuongnam/NamadaExplorerAndroid/assets/44250248/32f78dd3-eb8f-4b4b-8bd4-af841ecded65)
  - Validator details
   ![Validator-details](https://github.com/jokerphuongnam/NamadaExplorerAndroid/assets/44250248/6c7ff379-e257-486d-946d-90f72889d39c)
   
 - ### Blocks
  - Blocks
   ![Blocks](https://github.com/jokerphuongnam/NamadaExplorerAndroid/assets/44250248/d1fe1285-3437-4855-8b45-c9860cd9fac3)
  - Block details
   ![Block-details](https://github.com/jokerphuongnam/NamadaExplorerAndroid/assets/44250248/59f2a36a-42e2-4d43-886a-a41498163bb4)
   
 - ### Transactions
  - Transfers
   ![Transaction-Transfres](https://github.com/jokerphuongnam/NamadaExplorerAndroid/assets/44250248/a912379f-1094-4e2c-8a36-bc95b156748e)
  - Transfer details
   ![Transaction-Transfer-details](https://github.com/jokerphuongnam/NamadaExplorerAndroid/assets/44250248/c30dd724-3958-4109-8491-5a109d74802a)

  - Bonds
   ![Transaction-Bonds](https://github.com/jokerphuongnam/NamadaExplorerAndroid/assets/44250248/8402da31-bdd0-4078-acec-44bc89c591f8)
  - Bond details
   ![Transaction-bond-details](https://github.com/jokerphuongnam/NamadaExplorerAndroid/assets/44250248/a39a5d57-caf9-439f-86b8-5eced45c7eb0)
   
 - ### Governance
  - Governance stats
   ![Governace](https://github.com/jokerphuongnam/NamadaExplorerAndroid/assets/44250248/cd1ebae3-20e5-4d79-81e2-4cf4d9626582)
  - Proposals
   ![Governance-Proposals](https://github.com/jokerphuongnam/NamadaExplorerAndroid/assets/44250248/61f8bb90-339a-4641-bf6d-7d75a54d903c)
  - Proposal details
   ![Governance-proposal-details](https://github.com/jokerphuongnam/NamadaExplorerAndroid/assets/44250248/826d92f2-bb93-435f-b61d-ce0baccfdecb)

 - ### Parameters
  - Parameters expanded
   ![Parameters-expand](https://github.com/jokerphuongnam/NamadaExplorerAndroid/assets/44250248/5f124a37-203f-46ed-8d5b-06486eb0f4ae)
  - Parameters collapsed
   ![Parameters-collapsed](https://github.com/jokerphuongnam/NamadaExplorerAndroid/assets/44250248/ea8f39ea-769b-4b62-9354-11e7189aab8d)
  - Genesis validators
   ![Parameters-genesis-validators](https://github.com/jokerphuongnam/NamadaExplorerAndroid/assets/44250248/659cd0e4-8a57-4540-bd76-fd43f8c74866)

## Data Sources

Namada Explorer fetches data from various sources:

- [Supabase Instance 1](https://aauxuambgprwlwvfpksz.supabase.co)
- [Supabase Instance 2](https://tgwsikrpibxhbmtgrhbo.supabase.co)
- [Namada Website](https://namada.info)
- [Namada API](https://it.api.namada.red)
- [Namada RPC](https://namada-rpc.hadesguard.tech)

## Technical Details

- UI: Jetpack Compose
- Dependency Injection: Dagger Hilt
- API Calls: Retrofit
- File Reading (TOML): com.moandjiezana.toml
- String Parsing: Gson
- Serialization: Parcelize
- Minimum SDK Version: 26 (Android 8.0)
