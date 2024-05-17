# Pharmacy Management Application Documentation
## Creators
### This project was made by
- AbdelRahman Rahal — 221001443
- Mahmoud Mohamed — 221001313
- Nour Elsharkawy — 221001458

## How to Use It
### Adding Drugs to the Pharmacy
1. Open the application and click on the "Add Drug" button.
2. Fill in the required fields: name, ID, price, category, and available quantity.
3. Click "OK" to add the drug to the pharmacy inventory.

### Removing Drugs from the Pharmacy
1. Click on the "Remove Drug" button.
2. Enter the ID of the drug you wish to remove.
3. Click "OK" to confirm removal.
4. 
### Placing Orders
1. Click on the "Place An Order" button.
2. Enter the ID of the drug and the desired Quantity.
3. Click "Add" to add the drug to the cart.
4. Once done adding items, click "Buy" to finalize the purchase.
5. 
### Viewing Sales History
Click on the "View Sales" button to see a summary of all transactions including total sales.

## How It Works Under the Hood
### Pharmacy Class
- **Initialization:** The `Pharmacy` class initializes with a specified `capacity`. It maintains lists of `drugs` and `transactions`.
- **Adding Drugs:** When adding a drug, checks are performed to ensure the drug doesn't already exist, its details are valid, and the pharmacy isn't over capacity.
- **Removing Drugs:** Drugs can be removed by their `ID`. The system searches for the drug and removes it if found.
- **Placing Orders:** Orders adjust the available quantity of drugs and record transactions. A 20% surcharge applies to cosmetics.
- **Validating Drugs:** Ensures drugs have valid IDs, names, prices, categories, and quantities.
- **Finding Drugs:** Searches for drugs by `ID`.
### Drug Class

Represents individual drugs with properties such as `id`, `name`, `price`, `category`, and `availableQuantity`.
### Transaction Class

Records each sale with details about the drug sold, quantity, and total price.
### GUI Components

- **`AddDrugDialog`:** Allows users to input drug details and add them to the pharmacy.
- **`RemoveDrugDialog`:** Prompts for a drug `ID` to remove from the inventory.
- **`PlaceOrderDialog`:** Facilitates placing orders by selecting drugs and specifying quantities.
- **`PharmacyApp`:** Main application window where users interact with the pharmacy management system.
- 
## Some Complicated Methods Broken Down
### `Pharmacy.isValidDrug()`
This method checks whether a `Drug` object meets certain criteria to be considered valid. It returns `true` if the drug passes all checks, otherwise `false`.

#### Parameters:
- **`Drug drug`:** The drug object to validate.
- 
#### Checks Performed:
- The drug's ID is not empty.
- The drug's name is not empty.
- The drug's price is greater than or equal to 0.
- The drug's category is not empty.
- The drug's available quantity is greater than 0.

Returns: `boolean`

### `Pharmacy.findDrugById()`
This method searches for a `Drug` object within the pharmacy's inventory based on its ID. It uses Java streams to filter through the list of drugs and returns the first match found, or `null` if no match is found.

#### Parameters:
- **`String id`:** The ID of the drug to find.

Returns: `Drug` object if found, otherwise `null`.

### `AddDrugDialog.onOK()`
This method is triggered when the OK button is pressed in the `AddDrugDialog`. It attempts to add a new drug to the pharmacy's inventory after validating the input fields. Depending on the outcome, it displays different messages to the user.

#### Actions Performed:
- Validates the input fields for the new drug.
- Creates a new `Drug` object with the validated inputs.
- Attempts to add the drug to the pharmacy using the `addDrug()` method.
- Displays a message box indicating success or failure based on the result of the addition attempt.

### `PlaceOrderDialog.onViewCart()`
This method displays the contents of the shopping cart in a dialog box. It calculates the total price of items in the cart and formats the display accordingly.

#### Actions Performed:
- Iterates over each `OrderItem` in the cart.
- Retrieves the corresponding `Drug` object for each item.
- Calculates the total price for each item, applying a 20% surcharge for cosmetics.
- Accumulates the total cart price.
- Formats and displays the cart contents along with the total price in a message dialog.

### `viewSalesButton.addActionListener()`
This action listener is attached to the `viewSalesButton` in the `PharmacyApp` class. When the button is clicked, it retrieves the list of transactions from the pharmacy, calculates the total sales, and displays them in a formatted string in a dialog box.

#### Actions Performed:
- Retrieves the list of transactions from the pharmacy.
- Iterates over each transaction, appending details about the drug sold, quantity, and total price to a `StringBuilder`.
- Calculates the total sales amount.
- Displays the transaction history and total sales in a message dialog.