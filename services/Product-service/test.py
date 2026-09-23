import unittest
from unittest.mock import MagicMock, patch
from dataclasses import dataclass
from typing import Optional




class ProductCategory():
   BOOK = "BOOK"
   FOOD = "FOOD"

@dataclass
class Product:
    productId: str
    productName:str
    price:int 
    category: ProductCategory 
    quantity: int

@dataclass
class ProductRequest:
    productId: str
    productName: str
    price: int
    category: ProductCategory
    quantity: int


class ProductMapper:
   """ ?? """
   def to_product(self, request: ProductRequest) -> Product:
      if request is None:
         return None
      return Product(
         productId=request.productId,
         productName=request.productName,
         price=request.price,
         category=request.category,
         quantity=request.quantity
      )


class ProductService:
   def __init__(self, repository, mapper: ProductMapper):
      self.repository = repository
      self.mapper = mapper

   def create_product(self, request: ProductRequest) -> str:
      product = self.repository.save(self.mapper.to_product(request))
      return product.productId



class TestCustomerService(unittest.TestCase):
   def setUp(self):
      self.mock_repo = MagicMock()
      self.mock_mapper = MagicMock()
      self.service = ProductService(self.mock_repo, self.mock_mapper)


   def test_product(self):
      request = ProductRequest(
         productId="X147",
         productName="Bread",
         price=9.90,
         category="FOOD",
         quantity=10
      )
      mapped_product = Product(
         productId="X147",
         productName="Bread",
         price=9.90,
         category="FOOD",
         quantity=10
      )
      self.mock_mapper.to_product.return_value = mapped_product
      self.mock_repo.save.return_value = mapped_product

      result = self.service.create_product(request)
      self.mock_mapper.to_product.assert_called_once_with(request)

      self.mock_repo.save.assert_called_once_with(mapped_product)

      self.assertEqual(result, "X147")

unittest.main()