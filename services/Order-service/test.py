import unittest
from unittest.mock import MagicMock, patch
from dataclasses import dataclass
from typing import Optional
import datetime
from enum import Enum

"""
@dataclass
class order:
  id: id
  reference: str
  totalAmount: int
  paymentMethod: paymentMethod #class
  customerId: str
  orderLine[]: OrderLine #class
  createdAt: datetime
  lastModifiedDate: datetime


@dataclass
class orderRequest: 
  id: int
  customerId: str
  reference: str
  totalAmount: int
  paymentMethod: paymentMethod #class
  products[]: PurchaseRequest # class


@dataclass
class PaymentMethod(Enum):
    VISA
    CREDIT_CARD
    PAYPAL
    MASTERCARD

@dataclass
class orderLine:
    id: int 
    order: Order
    productId: str
    quantity: int


@dataclass
class CustomerResponse:
    id: str
    firstname: str
    lastname: str
    email: str


@dataclass
class paymentRequest:
    amount: int
    paymentMethod: paymentMethod
    orderId: int
    orderReference: str
    customer: customerResponse
"""


@dataclass
class OrderRequest:
  id: int
  customerId: str
  reference: str
  totalAmount: int
  paymentMethod: PaymentMethod
  products: Optional[PurchaseRequest] = None


@dataclass
class PaymentMethod(Enum):
  VISA
  CREDIT_CARD
  PAYPAL
  MASTERCARD

@dataclass
class PurchaseRequest:
  productId: str
  quantity: int












#----------------------------------------
# tests
#----------------------------------------


class TestOrder















