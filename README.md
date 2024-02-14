# Namada Explorer

Namada Explorer is an Android application designed to explore the Namada blockchain network. It allows users to view essential blockchain data and navigate through different screens to access specific information conveniently.

## Installation

You can install Namada Explorer by downloading the provided APK file from the repository and installing it on your Android device.

## Features

### Navigation

The app features a user-friendly navigation system, enabling users to move between different screens:

- **Main Screen**: From the main screen, users can navigate to:
  - Home Screen
  - Validators Screen
  - Blocks Screen
  - Transactions Screen
  - Governance Screen
  - Parameters Screen
  Additionally, users can access external web links:
  - [Namada Shielded Expedition NEBB](https://namada.net/shielded-expedition)
  - [Documentation](https://docs.namada.info/)

- **Home Screen**: Displays essential statistics such as epoch, block height, total stake, validators, Governance Proposals, and Chain ID. It also showcases the top 10 validators and top 10 blocks.

- **Validators Screen**: Provides statistics and a comprehensive list of all validators.

- **Blocks Screen**: Shows a list of all blocks on the blockchain.

- **Transactions Screen**: Displays transfer and bond transactions.

- **Governance Screen**: Shows proposals related to governance.

- **Parameters Screen**: Provides information on various parameters including Genesis Parameters, Chain Parameters, Proof of Stake Parameters, Governance Parameters, and Genesis Validators.

### Validator Details

Users can click on items from validators and blocks to get the validator's address and navigate to the Validator Detail screen. 

- **Validator Detail Screen**: Displays detailed information about a validator and the latest 10 blocks from the validator.

### Data Sources

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
- Minimum SDK Version: 26
