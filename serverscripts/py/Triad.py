class Triad:
	def __init__ (self, p1, p2, p3):
		if p1:
			self.p1 = p1
		else:
			self.p1 = ""

		if p2:
			self.p2 = p2
		else:
			self.p2 = ""
			
		if p3:
			self.p3 = p3
		else:
			self.p3 = ""

	def isFull(self):
		if self.p3:
			return True
		else:
			return False

	def addPerson(self, p):
		if not self.p1:
			self.p1 = p
			return True
		elif not self.p2:
			self.p2 = p
			return True
		elif not self.p3:
			self.p3 = p
			return True

		return False