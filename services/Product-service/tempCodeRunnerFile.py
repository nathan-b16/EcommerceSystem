    def setUp(self):
     self.mock_repo = MagicMock()
     self.mock_mapper = MagicMock();
     self.service = ProductService(self.mock_repo, self.mock_mapper)
