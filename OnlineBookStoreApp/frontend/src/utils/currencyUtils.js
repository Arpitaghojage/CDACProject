// Currency utilities for Indian Rupees
export const formatINR = (amount) => {
  const numericAmount = typeof amount === 'number' ? amount : Number(amount);
  if (Number.isNaN(numericAmount)) {
    return '₹0.00';
  }
  return `₹${numericAmount.toFixed(2)}`;
};
