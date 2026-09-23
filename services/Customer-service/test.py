"""
Unit tests for CustomerService — Python equivalents to illustrate
the Java logic without a running Spring context.

These mirror two Java JUnit 5 / Mockito tests you'd write for:
  1. createCustomer  — happy path: service saves and returns the new ID
  2. findById        — sad path:   service throws when the customer is missing
"""

import unittest
from unittest.mock import MagicMock, patch
from dataclasses import dataclass
from typing import Optional


# ---------------------------------------------------------------------------
# Minimal stand-ins for the Java classes
# ---------------------------------------------------------------------------

@dataclass
class Address:
    country: str
    city: str
    zipcode: str


@dataclass
class CustomerRequest:
    id: Optional[str]
    firstname: str
    lastname: str
    email: str
    address: Optional[Address] = None


@dataclass
class CustomerResponse:
    id: str
    firstname: str
    lastname: str
    email: str
    address: Optional[Address] = None


@dataclass
class Customer:
    id: str
    firstname: str
    lastname: str
    email: str
    address: Optional[Address] = None


class CustomerNotFoundException(RuntimeError):
    """Mirrors Customer.Exception.CustomerNotFoundException"""
    pass


class CustomerMapper:
    """Mirrors Customer.Service.CustomerMapper"""

    def to_customer(self, request: CustomerRequest) -> Customer:
        if request is None:
            return None
        return Customer(
            id=request.id,
            firstname=request.firstname,
            lastname=request.lastname,
            email=request.email,
            address=request.address,
        )
    
    def from_customer(self, customer: Customer) -> CustomerResponse:
        return CustomerResponse(
            id=customer.id,
            firstname=customer.firstname,
            lastname=customer.lastname,
            email=customer.email,
            address=customer.address,
        )


class CustomerService:
    """Mirrors Customer.Service.CustomerService"""

    def __init__(self, repository, mapper: CustomerMapper):
        self._repo = repository
        self._mapper = mapper

    def create_customer(self, request: CustomerRequest) -> str:
        customer = self._repo.save(self._mapper.to_customer(request))
        return customer.id

    def find_by_id(self, customer_id: str) -> CustomerResponse:
        customer = self._repo.find_by_id(customer_id)
        if customer is None:
            raise CustomerNotFoundException(
                f"no Customer with the Id: {customer_id}"
            )
        return self._mapper.from_customer(customer)


# ---------------------------------------------------------------------------
# Tests
# ---------------------------------------------------------------------------

class TestCustomerService(unittest.TestCase):

    def setUp(self):
        """Create fresh mocks and the service under test before each test."""
        self.mock_repo = MagicMock()
        self.mapper = CustomerMapper()          # real mapper — no need to mock it
        self.service = CustomerService(self.mock_repo, self.mapper)

    # ------------------------------------------------------------------
    # Test 1 — createCustomer happy path
    # ------------------------------------------------------------------
    def test_create_customer_returns_saved_id(self):
        """
        GIVEN a valid CustomerRequest
        WHEN  createCustomer is called
        THEN  the repository saves the mapped customer
              AND the generated ID is returned
        """
        request = CustomerRequest(
            id=None,
            firstname="Alice",
            lastname="Smith",
            email="alice@example.com",
            address=Address(country="US", city="New York", zipcode="10001"),
        )

        # Simulate what MongoDB returns after save (auto-generated ID)
        saved_customer = Customer(
            id="generated-mongo-id-123",
            firstname="Alice",
            lastname="Smith",
            email="alice@example.com",
        )
        self.mock_repo.save.return_value = saved_customer

        result = self.service.create_customer(request)

        # The repository must have been called exactly once
        self.mock_repo.save.assert_called_once()

        # The returned value must be the ID that the repository assigned
        self.assertEqual(result, "generated-mongo-id-123")

    # ------------------------------------------------------------------
    # Test 2 — findById sad path (customer not found)
    # ------------------------------------------------------------------
    def test_find_by_id_raises_when_customer_missing(self):
        """
        GIVEN an ID that does not exist in the repository
        WHEN  findById is called
        THEN  CustomerNotFoundException is raised with a descriptive message
        """
        missing_id = "non-existent-id"
        self.mock_repo.find_by_id.return_value = None   # simulate "not found"

        with self.assertRaises(CustomerNotFoundException) as ctx:
            self.service.find_by_id(missing_id)

        # Verify the exception message contains the missing ID
        self.assertIn(missing_id, str(ctx.exception))

    # ------------------------------------------------------------------
    # Test 3 — 
    # ------------------------------------------------------------------




if __name__ == "__main__":
    unittest.main(verbosity=2)
