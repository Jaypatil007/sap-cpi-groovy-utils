# SAP CPI Groovy Utilities

A collection of reusable Groovy scripts for SAP Cloud Platform Integration (CPI) to simplify common integration tasks and promote code reuse.

## Getting Started

1. Clone this repository to access the Groovy utility scripts
2. Copy the desired script content into your CPI integration flow
3. Configure the script parameters as needed

## Available Utilities

### Message Logging Utilities
- **log-attachments.groovy**: Configurable message attachment logging with property-based enablement

## Usage Example

For the log-attachments script:

1. Add the Groovy script to your integration flow
2. In the SAP CPI groovy script palette, set the "Script Function" to the wrapper function you want to call (e.g., `log01`)
3. Set the message property `Logconfig` to 'true' (case-insensitive) to enable logging

## Contributing

To add new utilities:
1. Create a new directory under `src/` for your utility category
2. Add your Groovy script with proper documentation
3. Update the README with information about your utility
4. Follow the established naming and documentation conventions

## License

This project is licensed under the MIT License - see the LICENSE file for details.
