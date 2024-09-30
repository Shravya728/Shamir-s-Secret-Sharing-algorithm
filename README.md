# Shamir Secret Sharing Algorithm in Java

This project implements the Shamir Secret Sharing algorithm in Java, demonstrating both standard polynomial interpolation and Galois Field arithmetic. It visualizes the shares generated using the algorithm with scatter plots.

## Overview

The Shamir Secret Sharing algorithm is a cryptographic method used to divide a secret into multiple shares, ensuring that a minimum number of shares (threshold) is required to reconstruct the original secret. This project includes two implementations:
- *Without Galois Field arithmetic*.
- *With Galois Field arithmetic* (not fully implemented in this version).

## Features

- Command-line argument parsing for secret, shares, and threshold.
- Generates shares based on the specified secret.
- Visualizes the generated shares using JFreeChart.
- Prints the reconstructed secret from the shares.

## Requirements

- JDK 8 or later
- Apache Commons CLI library
- JFreeChart library

